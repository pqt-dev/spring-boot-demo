package com.demo.spring_boot.controller;

import com.demo.spring_boot.dto.category.CreateCategoryRequest;
import com.demo.spring_boot.dto.category.UpdateCategoryRequest;
import com.demo.spring_boot.entity.Category;
import com.demo.spring_boot.entity.Post;
import com.demo.spring_boot.exception.ResourceNotFoundException;
import com.demo.spring_boot.repository.CategoryRepository;
import com.demo.spring_boot.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryResponseController {
    final CategoryRepository repository;
    final PostRepository postRepository;

    @Autowired
    public CategoryResponseController(CategoryRepository repository, PostRepository postRepository) {
        this.repository = repository;
        this.postRepository = postRepository;
    }

    @GetMapping("/categories")
    public Page<Category> fetch(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return repository.findAll(PageRequest.of(page, size, Sort.by("id").descending()));
    }

    @PostMapping("/categories")
    public Category add(@RequestBody CreateCategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        final List<Post> posts = postRepository.findAllById(request.getPostIds());
        category.setPosts(posts.stream().peek((value) -> value.getCategories().add(category)).toList());
        return repository.save(category);
    }

    @PatchMapping("/categories/{id}")
    public ResponseEntity<Category> update(@PathVariable() Long id, @RequestBody() UpdateCategoryRequest request) {
        return repository.findById(id).map((value) -> {
            if (request.getName() != null) value.setName(request.getName());
            if (request.getDescription() != null) value.setDescription(request.getDescription());
            if (request.getPostIds() != null) {
                final List<Post> posts = postRepository.findAllById(request.getPostIds());
                value.setPosts(posts.stream().peek((post) -> post.getCategories().add(value)).toList());
            }
            return ResponseEntity.ok(repository.save(value));
        }).orElseThrow(() -> new ResourceNotFoundException("Category", id));
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?> delete(@PathVariable() Long id) {
        return repository.findById(id).map((value) -> {
            value.getPosts().forEach((post) -> post.getCategories().remove(value));
            value.getPosts().clear();
            repository.delete(value);
            return ResponseEntity.noContent().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Category", id));
    }
}
