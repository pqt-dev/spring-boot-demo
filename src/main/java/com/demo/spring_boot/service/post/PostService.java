package com.demo.spring_boot.service.post;

import com.demo.spring_boot.dto.post.CreatePostRequest;
import com.demo.spring_boot.dto.post.PostMapper;
import com.demo.spring_boot.dto.post.PostResponse;
import com.demo.spring_boot.dto.post.UpdatePostRequest;
import com.demo.spring_boot.entity.author.Author;
import com.demo.spring_boot.entity.category.Category;
import com.demo.spring_boot.entity.post.Post;
import com.demo.spring_boot.exception.ResourceNotFoundException;
import com.demo.spring_boot.repository.AuthorRepository;
import com.demo.spring_boot.repository.CategoryRepository;
import com.demo.spring_boot.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {
    private final PostRepository repository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final PostMapper mapper;

    public PostService(PostRepository repository, AuthorRepository authorRepository,
                       CategoryRepository categoryRepository,
                       PostMapper mapper) {
        this.repository = repository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    public Page<PostResponse> findAll(Pageable pageable) {
        return repository.findAllBy(pageable)
                         .map(mapper::toResponse);
    }

    @Transactional
    public PostResponse add(CreatePostRequest request, String email) {
        final Author author = authorRepository.findByEmail(email)
                                              .orElseThrow(() -> new ResourceNotFoundException("Author",
                                                      email));
        final List<Category> categories = categoryRepository.findAllById(request.getCategories());
        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setAuthor(author);
        post.setCategories(categories);
        return mapper.toResponse(repository.save(post));
    }

    @Transactional
    public PostResponse update(UpdatePostRequest request, Long id, String email) {
        Post post = repository.findById(id)
                              .orElseThrow(() -> new ResourceNotFoundException("Post", id));
        final Author author = authorRepository.findByEmail(email)
                                              .orElseThrow(() -> new ResourceNotFoundException("Author",
                                                      email));
        if (author != null) {
            post.setAuthor(author);
        }
        if (request.getTitle() != null) {
            post.setTitle(request.getTitle());
        }
        if (request.getContent() != null) {
            post.setContent(request.getContent());
        }
        final List<Category> categories = categoryRepository.findAllById(request.getCategories());
        if (!categories.isEmpty()) {
            post.setCategories(categories);
        }
        return mapper.toResponse(repository.save(post));
    }


}
