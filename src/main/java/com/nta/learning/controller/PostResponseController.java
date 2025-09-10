package com.nta.learning.controller;

import com.nta.learning.dto.post.CreatePostRequest;
import com.nta.learning.entity.Author;
import com.nta.learning.entity.Category;
import com.nta.learning.entity.Post;
import com.nta.learning.exception.ResourceNotFoundException;
import com.nta.learning.repository.AuthorRepository;
import com.nta.learning.repository.CategoryRepository;
import com.nta.learning.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostResponseController {
    final PostRepository repository;
    final AuthorRepository authorRepository;
    final CategoryRepository categoryRepository;

    @Autowired
    public PostResponseController(PostRepository repository, AuthorRepository authorRepository, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/posts")
    public Page<Post> allPosts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        final Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return repository.findAll(pageable);
    }

    @PostMapping("/posts")
    public Post add(@RequestBody CreatePostRequest request) {
        final Author author = authorRepository.findById(request.getAuthorId()).orElseThrow(() -> new ResourceNotFoundException("Author", request.getAuthorId()));
        final List<Category> categories = categoryRepository.findAllById(request.getCategories());
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setAuthor(author);
        post.setCategories(categories);
        return repository.save(post);
    }
}
