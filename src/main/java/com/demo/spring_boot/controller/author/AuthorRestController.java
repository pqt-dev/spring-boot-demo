package com.demo.spring_boot.controller.author;

import com.demo.spring_boot.dto.author.AuthorResponse;
import com.demo.spring_boot.dto.author.CreateAuthorRequest;
import com.demo.spring_boot.dto.author.UpdateAuthorRequest;
import com.demo.spring_boot.service.author.AuthorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthorRestController {
    final private AuthorService service;

    @Autowired
    public AuthorRestController(AuthorService service) {
        this.service = service;
    }

    @GetMapping("/authors")
    public Page<AuthorResponse> fetch(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        final Pageable pageable = PageRequest.of(page, size, Sort.by("id")
                                                                 .descending());
        return service.findAll(pageable);
    }

    @PostMapping("/authors/search")
    public Page<AuthorResponse> searchByKeyword(@RequestParam(defaultValue = "") String keyword,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size) {
        final Pageable pageable = PageRequest.of(page, size, Sort.by("id")
                                                                 .descending());
        return service.searchByKeyword(keyword, pageable);
    }

    @PostMapping("/authors")
    public ResponseEntity<AuthorResponse> add(@Valid @RequestBody CreateAuthorRequest request) {

        return new ResponseEntity<>(service.add(request), HttpStatus.CREATED);
    }

    @PatchMapping("/authors/{id}")
    public ResponseEntity<AuthorResponse> update(@PathVariable Long id,
                                                 @Valid @RequestBody UpdateAuthorRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/authors/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent()
                             .build();
    }

}
