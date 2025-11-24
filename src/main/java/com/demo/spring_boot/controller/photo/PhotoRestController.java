package com.demo.spring_boot.controller.photo;

import com.demo.spring_boot.config.EnvConfig;
import com.demo.spring_boot.dto.photo.PhotoDto;
import com.demo.spring_boot.repository.AuthorRepository;
import com.demo.spring_boot.response.ApiResponse;
import com.demo.spring_boot.service.photo.FirebasePhotoService;
import com.demo.spring_boot.service.photo.LocalPhotoService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
public class PhotoRestController {

    final private AuthorRepository authorRepository;
    final private EnvConfig envConfig;
    final private LocalPhotoService localPhotoService;
    final private FirebasePhotoService firebasePhotoService;

    @Autowired
    public PhotoRestController(AuthorRepository authorRepository, EnvConfig envConfig,
                               LocalPhotoService localPhotoService, FirebasePhotoService firebasePhotoService
    ) {
        this.authorRepository = authorRepository;
        this.envConfig = envConfig;
        this.localPhotoService = localPhotoService;
        this.firebasePhotoService = firebasePhotoService;
    }

    @GetMapping("/my_photos")
    ResponseEntity<ApiResponse<Page<PhotoDto>>> fetch(@AuthenticationPrincipal Claims claims,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) throws IOException {
        final Pageable pageable = PageRequest.of(page, size, Sort.by("id")
                                                                 .descending());
        final var email = claims.get("email", String.class);
        final var author = authorRepository.findByEmail(email)
                                           .orElseThrow();
        final Page<PhotoDto> response;
        if (envConfig.isProd()) {
            response = firebasePhotoService.findPhotosByAuthor(author, pageable)
                                           .map(data -> PhotoDto.builder()
                                                                .id(data.getId())
                                                                .photoUrl(data.getPhotoUrl())
                                                                .uploadedAt(
                                                                        data.getUploadedAt())
                                                                .isLocal(data.getIsLocal())
                                                                .build());
        } else {
            response = localPhotoService.findPhotosByAuthor(author, pageable)
                                        .map(data -> PhotoDto.builder()
                                                             .id(data.getId())
                                                             .photoUrl(data.getPhotoUrl())
                                                             .uploadedAt(
                                                                     data.getUploadedAt())
                                                             .isLocal(data.getIsLocal())
                                                             .build());
        }
        return ResponseEntity.ok(ApiResponse.<Page<PhotoDto>>builder()
                                            .data(response)
                                            .build());
    }


}
