package com.demo.spring_boot.service.photo;

import com.demo.spring_boot.entity.author.Author;
import com.demo.spring_boot.entity.photo.Photo;
import com.demo.spring_boot.repository.AuthorRepository;
import com.demo.spring_boot.repository.PhotoRepository;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
//@Profile("dev")
public class LocalPhotoService implements PhotoService {
    final AuthorRepository authorRepository;
    final private PhotoRepository repository;
    @Value("${app.storage.local.path}")
    private String uploadDirectory;

    public LocalPhotoService(PhotoRepository repository, AuthorRepository authorRepository) {
        this.repository = repository;
        this.authorRepository = authorRepository;
    }

    @Override
    public Page<Photo> findPhotosByAuthor(Author author, Pageable pageable) {
        return repository.findByAuthorAndIsLocalTrue(author, pageable);
    }

    @Override
    public List<String> upload(List<MultipartFile> files) throws IOException {
        List<String> response = new ArrayList<>();
        String rootPath = System.getProperty("user.dir");
        Path uploadPath = Paths.get(rootPath, uploadDirectory);
        final var authentication = SecurityContextHolder.getContext()
                                                        .getAuthentication();
        final Claims claims = (Claims) authentication.getPrincipal();
        final String email = (String) claims.get("email");
        final Author author = authorRepository.findByEmail(email)
                                              .orElseThrow();
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        for (MultipartFile file : files) {
            final String path = singleUpload(file);
            final var photo = savePhoto(author, path);
            response.add(photo.getPhotoUrl());
        }
        return response;
    }

    @Override
    public Photo savePhoto(Author author, String url) {
        final var response = repository.save(Photo.builder()
                                                  .author(author)
                                                  .photoUrl(url)
                                                  .isLocal(true)
                                                  .build());
        repository.flush();
        return response;
    }

    private String singleUpload(MultipartFile file) throws IOException {
        String rootPath = System.getProperty("user.dir");
        Path uploadPath = Paths.get(rootPath, uploadDirectory);
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String uniqueFilename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(uniqueFilename);
        file.transferTo(filePath.toFile());
        return "/" + uploadDirectory + "/" + uniqueFilename;
    }
}
