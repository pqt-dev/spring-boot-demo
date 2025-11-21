package com.demo.spring_boot.service.photo;

import com.demo.spring_boot.entity.author.Author;
import com.demo.spring_boot.entity.photo.Photo;
import com.demo.spring_boot.repository.AuthorRepository;
import com.demo.spring_boot.repository.PhotoRepository;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
//@Profile("prod")
public class FirebasePhotoService implements PhotoService {
    final AuthorRepository authorRepository;
    final private Storage storage;
    final private ImageValidationService imageValidationService;
    final private PhotoRepository repository;
    @Value("${firebase.bucket-name}")
    private String bucketName;
    @Value("${firebase.blob-path}")
    private String blobPath;


    public FirebasePhotoService(Storage storage,
                                ImageValidationService imageValidationService, PhotoRepository repository,
                                AuthorRepository authorRepository) {
        this.storage = storage;
        this.imageValidationService = imageValidationService;
        this.repository = repository;
        this.authorRepository = authorRepository;
    }

    @Override
    public Page<Photo> findPhotosByAuthor(Author author, Pageable pageable) {
        return repository.findByAuthorAndIsLocalFalse(author, pageable);
    }

    @Override
    public List<String> upload(List<MultipartFile> files) throws IOException {
        List<String> response = new ArrayList<>();
        final var authentication = SecurityContextHolder.getContext()
                                                        .getAuthentication();
        final Claims claims = (Claims) authentication.getPrincipal();
        final String email = (String) claims.get("email");
        final Author author = authorRepository.findByEmail(email)
                                              .orElseThrow();
        for (MultipartFile multipartFile : files) {
            final String url = uploadFile(multipartFile);
            final var photo = savePhoto(author, url);
            response.add(photo.getPhotoUrl());
        }
        return response;
    }

    @Override
    public Photo savePhoto(Author author, String url) {
        final var response = repository.save(Photo.builder()
                                                  .author(author)
                                                  .photoUrl(url)
                                                  .isLocal(false)
                                                  .build());
        repository.flush();
        return response;
    }

    private String uploadFile(MultipartFile file) throws IOException {
        // 1. Validate file
        imageValidationService.validateImage(file);

        // 2. Generate unique filename
        String originalFilename = file.getOriginalFilename();
        assert originalFilename != null;
        String uniqueFilename = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // 3. Create blob (file path in Firebase)
        String blobPath = this.blobPath + uniqueFilename;
        BlobId blobId = BlobId.of(bucketName, blobPath);

        String uuid = UUID.randomUUID()
                          .toString();
        Map<String, String> metadata = new HashMap<>();
        metadata.put("'token'", uuid);


        // 4. Set file metadata
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                                    .setContentType(file.getContentType())
                                    .setMetadata(metadata)
                                    .build();

        // 5. Upload to Firebase
        Blob blob = storage.create(blobInfo, file.getBytes());


        // 6. Return public URL
        return String.format(
                "/v0/b/%s/o/%s?alt=media&token=%s",
                bucketName,
                blob.getName()
                    .replace("/", "%2F"),
                uuid

        );
    }
}
