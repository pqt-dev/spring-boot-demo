package com.demo.spring_boot.repository;

import com.demo.spring_boot.entity.author.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @EntityGraph(attributePaths = "posts")
    @Query("""
                SELECT a FROM Author a
                WHERE lower(a.name) LIKE lower(concat('%', :keyword, '%'))
                   OR lower(a.address) LIKE lower(concat('%', :keyword, '%'))
                   OR lower(a.phone) LIKE lower(concat('%', :keyword, '%'))
            """)
    Page<Author> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    Optional<Author> findByEmail(String email);

    boolean existsByEmail(String email);
}



