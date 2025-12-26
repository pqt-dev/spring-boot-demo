package com.demo.spring_boot.controller.category;

import com.demo.spring_boot.dto.category.CategoryRequest;
import com.demo.spring_boot.dto.category.CategoryResponse;
import com.demo.spring_boot.service.CategoryService;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryRestController {
    final private CategoryService service;

    public CategoryRestController(CategoryService service) {
        this.service = service;
    }

    @GetMapping("/categories")
    public Page<CategoryResponse> fetch(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        final Pageable pageable = PageRequest.of(page, size, Sort.by("id")
                                                                 .descending());
        return service.findAll(pageable);
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryResponse> add(@RequestBody CategoryRequest request) {
        return ResponseEntity.ok(service.add(request));
    }

    @PatchMapping("/categories/{id}")
    public ResponseEntity<CategoryResponse> update(@PathVariable() Long id, @RequestBody() CategoryRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?> delete(@PathVariable() Long id) throws BadRequestException {
        service.delete(id);
        return ResponseEntity.noContent()
                             .build();
    }
}
