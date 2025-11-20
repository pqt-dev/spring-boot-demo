package com.demo.spring_boot.controller.post;

import com.demo.spring_boot.dto.post.CreatePostRequest;
import com.demo.spring_boot.entity.author.Author;
import com.demo.spring_boot.entity.category.Category;
import com.demo.spring_boot.entity.post.Post;
import com.demo.spring_boot.exception.ResourceNotFoundException;
import com.demo.spring_boot.repository.AuthorRepository;
import com.demo.spring_boot.repository.CategoryRepository;
import com.demo.spring_boot.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostRestController {
    final PostRepository repository;
    final AuthorRepository authorRepository;
    final CategoryRepository categoryRepository;

    @Autowired
    public PostRestController(PostRepository repository, AuthorRepository authorRepository,
                              CategoryRepository categoryRepository) {
        this.repository = repository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/posts")
    public Page<Post> allPosts(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size) {
        final Pageable pageable = PageRequest.of(page, size, Sort.by("id")
                                                                 .descending());
        return repository.findAll(pageable);
    }

    @PostMapping("/posts")
    public Post add(@RequestBody CreatePostRequest request) {
        final Author author = authorRepository.findById(request.getAuthorId())
                                              .orElseThrow(() -> new ResourceNotFoundException("Author",
                                                      request.getAuthorId()));
        final List<Category> categories = categoryRepository.findAllById(request.getCategories());
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setAuthor(author);
        post.setCategories(categories);
        return repository.save(post);
    }
}
