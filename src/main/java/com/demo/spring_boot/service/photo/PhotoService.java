package com.demo.spring_boot.service.photo;

import com.demo.spring_boot.dto.photo.PhotoMapper;
import com.demo.spring_boot.dto.photo.PhotoResponse;
import com.demo.spring_boot.entity.author.Author;
import com.demo.spring_boot.entity.photo.Photo;
import com.demo.spring_boot.repository.AuthorRepository;
import com.demo.spring_boot.repository.PhotoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class PhotoService {
    final private UploadPhotoService uploadPhotoService;
    final private AuthorRepository authorRepository;
    final private PhotoRepository repository;
    final private PhotoMapper mapper;

    public PhotoService(UploadPhotoService uploadPhotoService, AuthorRepository authorRepository,
                        PhotoRepository repository, PhotoMapper mapper) {
        this.uploadPhotoService = uploadPhotoService;
        this.authorRepository = authorRepository;
        this.repository = repository;
        this.mapper = mapper;
    }


    @Transactional
    public List<PhotoResponse> uploads(String email, List<MultipartFile> files) throws IOException {
        final Author author = authorRepository.findByEmail(email)
                                              .orElseThrow(
                                                      () -> new EntityNotFoundException("Author not found: " + email));
        final var uploadUrls = uploadPhotoService.upload(files);
        final var photos = uploadUrls.stream()
                                     .map(value -> new Photo(author, value))
                                     .toList();
        final var result = repository.saveAll(photos);
        return result.stream()
                     .map(mapper::toResponse)
                     .toList();
    }

    public Page<PhotoResponse> fetchUserPhotos(String email, int page, int size) {
        final Pageable pageable = PageRequest.of(page, size, Sort.by("id")
                                                                 .descending());
        return repository.findPhotosByAuthorEmail(pageable, email)
                         .map(mapper::toResponse);
    }
}
