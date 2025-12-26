package com.demo.spring_boot.service.author;

import com.demo.spring_boot.dto.author.AuthorMapper;
import com.demo.spring_boot.dto.author.AuthorResponse;
import com.demo.spring_boot.dto.author.CreateAuthorRequest;
import com.demo.spring_boot.dto.author.UpdateAuthorRequest;
import com.demo.spring_boot.entity.author.Author;
import com.demo.spring_boot.exception.ExistsEmailException;
import com.demo.spring_boot.exception.ResourceNotFoundException;
import com.demo.spring_boot.repository.AuthorRepository;
import com.demo.spring_boot.repository.PhotoRepository;
import com.demo.spring_boot.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class AuthorService {
    final private AuthorRepository repository;
    final private PhotoRepository photoRepository;
    final private PostRepository postRepository;
    final private AuthorMapper mapper;


    public AuthorService(AuthorRepository repository, PhotoRepository photoRepository, PostRepository postRepository,
                         AuthorMapper mapper) {
        this.repository = repository;
        this.photoRepository = photoRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    public Page<AuthorResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                         .map(mapper::toResponse);
    }

    public Optional<AuthorResponse> findByEmail(String email) {
        return repository.findByEmail(email)
                         .map(mapper::toResponse);
    }

    public Page<AuthorResponse> searchByKeyword(String keyword, Pageable pageable) {
        return repository.searchByKeyword(keyword, pageable)
                         .map(mapper::toResponse);
    }

    @Transactional
    public AuthorResponse add(CreateAuthorRequest request) {
        boolean isEmailExist = repository.existsByEmail(request.getEmail());
        if (isEmailExist) {
            throw new ExistsEmailException();
        }
        Author author = new Author();
        author.setName(request.getName());
        author.setAddress(request.getAddress());
        author.setPhone(request.getPhone());
        author.setJob(request.getJob());
        author.setEmail(request.getEmail());
        author.setPassword(request.getPassword());
        return mapper.toResponse(repository.save(author));
    }

    @Transactional
    public AuthorResponse update(Long id, UpdateAuthorRequest request) {
        Author author = repository.findById(id)
                                  .orElseThrow(() -> new ResourceNotFoundException("Author", id));
        if (request.getName() != null) author.setName(request.getName());
        if (request.getAddress() != null) author.setAddress(request.getAddress());
        if (request.getPhone() != null) author.setPhone(request.getPhone());
        if (request.getJob() != null) author.setJob(request.getJob());
        if (request.getAvatar() != null) author.setAvatar(request.getAvatar());
        return mapper.toResponse(repository.save(author));
    }

    @Transactional
    public void delete(Long id) {
        Author author = repository.findById(id)
                                  .orElseThrow(() -> new ResourceNotFoundException("Author", id));
        photoRepository.deleteByAuthorId(id);
        postRepository.detachAuthor(author.getId());
        repository.delete(author);
    }

}
