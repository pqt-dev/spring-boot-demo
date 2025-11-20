package com.demo.spring_boot.controller.photo;

import com.demo.spring_boot.dto.photo.PhotoDto;
import com.demo.spring_boot.repository.AuthorRepository;
import com.demo.spring_boot.response.ApiResponse;
import com.demo.spring_boot.service.photo.PhotoService;
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


@RestController
public class PhotoRestController {

    final private AuthorRepository authorRepository;
    final private PhotoService photoService;

    @Autowired
    public PhotoRestController(AuthorRepository authorRepository, PhotoService photoService) {
        this.authorRepository = authorRepository;
        this.photoService = photoService;
    }

    @GetMapping("/my_photos")
    ResponseEntity<ApiResponse<Page<PhotoDto>>> fetch(@AuthenticationPrincipal Claims claims,
                                                      @RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size) {
        final Pageable pageable = PageRequest.of(page, size, Sort.by("id")
                                                                 .descending());
        final var email = claims.get("email", String.class);
        final var author = authorRepository.findByEmail(email)
                                           .orElseThrow();
        return ResponseEntity.ok(ApiResponse.<Page<PhotoDto>>builder()
                                            .data(photoService.findPhotosByAuthor(author, pageable)
                                                              .map(response -> PhotoDto.builder()
                                                                                       .id(response.getId())
                                                                                       .photoUrl(response.getPhotoUrl())
                                                                                       .uploadedAt(
                                                                                               response.getUploadedAt())
                                                                                       .isLocal(response.getIsLocal())
                                                                                       .build()))
                                            .build());
    }


}
