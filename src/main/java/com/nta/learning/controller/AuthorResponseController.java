package com.nta.learning.controller;

import com.nta.learning.dto.author.CreateAuthorRequest;
import com.nta.learning.dto.author.UpdateAuthorRequest;
import com.nta.learning.entity.Author;
import com.nta.learning.exception.ResourceNotFoundException;
import com.nta.learning.repository.AuthorRepository;
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
public class AuthorResponseController {
    final AuthorRepository repository;

    @Autowired
    public AuthorResponseController(AuthorRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/authors")
    public Page<Author> fetch(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        final Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return repository.findAll(pageable);
    }

    @PostMapping("/authors")
    public ResponseEntity<Author> add(@Valid @RequestBody CreateAuthorRequest request) {
        Author author = new Author();
        author.setName(request.getName());
        author.setAddress(request.getAddress());
        author.setPhone(request.getPhone());
        author.setJob(request.getJob());
        return new ResponseEntity<>(author, HttpStatus.CREATED);
    }

    @PatchMapping("/authors/{id}")
    public ResponseEntity<Author> update(@PathVariable Long id, @Valid @RequestBody UpdateAuthorRequest request) {
        return repository.findById(id).map((value) -> {
            if (request.getName() != null) value.setName(request.getName());
            if (request.getAddress() != null) value.setAddress(request.getAddress());
            if (request.getPhone() != null) value.setPhone(request.getPhone());
            if (request.getJob() != null) value.setJob(request.getJob());
            return ResponseEntity.ok(repository.save(value));
        }).orElseThrow(() -> new ResourceNotFoundException("User", id));
    }

    @DeleteMapping("/authors/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return repository.findById(id).map((value) -> {
            repository.deleteById(value.getId());
            return ResponseEntity.noContent().build();
        }).orElseThrow(() -> new ResourceNotFoundException("User", id));
    }
}
