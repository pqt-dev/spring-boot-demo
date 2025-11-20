package com.demo.spring_boot.controller.author;

import com.demo.spring_boot.dto.author.CreateAuthorRequest;
import com.demo.spring_boot.dto.author.UpdateAuthorRequest;
import com.demo.spring_boot.entity.author.Author;
import com.demo.spring_boot.exception.ResourceNotFoundException;
import com.demo.spring_boot.repository.AuthorRepository;
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
    final AuthorRepository repository;

    @Autowired
    public AuthorRestController(AuthorRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/authors")
    public Page<Author> fetch(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        final Pageable pageable = PageRequest.of(page, size, Sort.by("id")
                                                                 .descending());
        return repository.findAll(pageable);
    }

    @PostMapping("/authors/search")
    public Page<Author> searchByKeyword(@RequestParam(defaultValue = "") String keyword,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        final Pageable pageable = PageRequest.of(page, size, Sort.by("id")
                                                                 .descending());
        return repository.findByNameContainingIgnoreCaseOrAddressContainingIgnoreCaseOrPhoneContainingIgnoreCase(
                keyword, keyword, keyword, pageable);
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
        return repository.findById(id)
                         .map((value) -> {
                             if (request.getName() != null) value.setName(request.getName());
                             if (request.getAddress() != null) value.setAddress(request.getAddress());
                             if (request.getPhone() != null) value.setPhone(request.getPhone());
                             if (request.getJob() != null) value.setJob(request.getJob());
                             return ResponseEntity.ok(repository.save(value));
                         })
                         .orElseThrow(() -> new ResourceNotFoundException("User", id));
    }

    @DeleteMapping("/authors/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return repository.findById(id)
                         .map((value) -> {
                             repository.deleteById(value.getId());
                             return ResponseEntity.noContent()
                                                  .build();
                         })
                         .orElseThrow(() -> new ResourceNotFoundException("User", id));
    }

//    @GetMapping(name = "/authors/{id}/photos")
//    public ResponseEntity<ApiResponse<List<Photo>>> fetchAuthorPhotos(@PathVariable Long id) {
//        final var author = repository.findById(id)
//                                     .orElseThrow(() -> new ResourceNotFoundException("Author", id));
//        final List<Photo> photos = author.getPhotos();
//        return ResponseEntity.ok(ApiResponse.<List<Photo>>builder()
//                                            .data(photos)
//                                            .build());
//    }
}
