package com.demo.spring_boot.repository;

import com.demo.spring_boot.entity.author.Author;
import com.demo.spring_boot.entity.photo.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    Page<Photo> findByAuthorAndIsLocalTrue(Author author, Pageable pageable);

    Page<Photo> findByAuthorAndIsLocalFalse(Author author, Pageable pageable);
}
