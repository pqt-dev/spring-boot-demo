package com.demo.spring_boot.controller.photo;

import com.demo.spring_boot.dto.photo.PhotoResponse;
import com.demo.spring_boot.response.ApiResponse;
import com.demo.spring_boot.service.photo.PhotoService;
import io.jsonwebtoken.Claims;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
public class PhotoRestController {

    final private PhotoService service;

    public PhotoRestController(
            PhotoService service) {
        this.service = service;
    }

    @GetMapping("/my_photos")
    ResponseEntity<ApiResponse<Page<PhotoResponse>>> fetch(
            @AuthenticationPrincipal Claims claims,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {


        final String email = (String) claims.get("email");
        return ResponseEntity.ok(ApiResponse.<Page<PhotoResponse>>builder()
                                            .data(service.fetchUserPhotos(email, page, size))
                                            .build());
    }

    @RequestMapping(value = "/uploads", method = RequestMethod.POST)
    public ResponseEntity<ApiResponse<List<PhotoResponse>>> uploadFile(@RequestParam("files") List<MultipartFile> files,
                                                                       @AuthenticationPrincipal Claims claims)
            throws IOException {
        final String email = (String) claims.get("email");
        final var photos = service.uploads(email, files);
        return ResponseEntity.ok(ApiResponse.<List<PhotoResponse>>builder()
                                            .data(photos)
                                            .build());
    }


}
