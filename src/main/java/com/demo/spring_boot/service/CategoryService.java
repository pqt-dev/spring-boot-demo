package com.demo.spring_boot.service;

import com.demo.spring_boot.dto.category.CategoryMapper;
import com.demo.spring_boot.dto.category.CategoryRequest;
import com.demo.spring_boot.dto.category.CategoryResponse;
import com.demo.spring_boot.entity.category.Category;
import com.demo.spring_boot.entity.post.Post;
import com.demo.spring_boot.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    final private CategoryRepository repository;
    final private CategoryMapper mapper;

    public CategoryService(CategoryRepository repository, CategoryMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }


    public Page<CategoryResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                         .map(mapper::toResponse);
    }

    public CategoryResponse add(CategoryRequest request) {
        final Category category = new Category(request.getName(), request.getDescription());
        return mapper.toResponse(repository.save(category));
    }

    public CategoryResponse update(Long id, CategoryRequest request) {
        final var category = repository.findById(id)
                                       .orElseThrow();
        category.update(request.getName(), request.getDescription());
        return mapper.toResponse(repository.save(category));
    }

    public void delete(Long id) {
        Category category = repository.findById(id)
                                      .orElseThrow(() -> new RuntimeException("Category not found"));
        for (Post post : category.getPosts()) {
            post.getCategories()
                .remove(category);
        }
        repository.delete(category);
    }


}
