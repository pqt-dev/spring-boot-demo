package com.demo.spring_boot.repository;

import com.demo.spring_boot.entity.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

//    @EntityGraph(attributePaths = {"author"})
//    Page<Post> findAll(@NonNull Pageable pageable);

    @EntityGraph(value = "Post.author")
    Page<Post> findAllBy(Pageable pageable);

    @Modifying
    @Query("update Post p set p.author = null where p.author.id = :authorId")
    void detachAuthor(@Param("authorId") Long authorId);

}
