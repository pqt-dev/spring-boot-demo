package com.demo.spring_boot.controller.post;

import com.demo.spring_boot.dto.post.CreatePostRequest;
import com.demo.spring_boot.dto.post.PostResponse;
import com.demo.spring_boot.dto.post.UpdatePostRequest;
import com.demo.spring_boot.service.post.PostService;
import io.jsonwebtoken.Claims;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostRestController {
    private final PostService service;

    public PostRestController(PostService service) {
        this.service = service;
    }


    @GetMapping("/posts")
    public Page<PostResponse> fetchPosts(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        final Pageable pageable = PageRequest.of(page, size, Sort.by("id")
                                                                 .descending());
        return service.findAll(pageable);
    }

    @PostMapping("/posts")
    public PostResponse add(@RequestBody CreatePostRequest request, @AuthenticationPrincipal Claims claims) {
        final String email = claims.get("email")
                                   .toString();
        return service.add(request, email);
    }

    @PatchMapping("/posts/{id}")
    public PostResponse update(@RequestBody UpdatePostRequest request, @PathVariable Long id,
                               @AuthenticationPrincipal Claims claims) {
        final String email = claims.get("email")
                                   .toString();
        return service.update(request, id, email);
    }


}
