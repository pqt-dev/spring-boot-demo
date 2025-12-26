package com.demo.spring_boot.entity.photo;

import com.demo.spring_boot.entity.author.Author;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "authors_photos")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String photoUrl;
    @Column(name = "uploaded_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime uploadedAt;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    public Photo(Author author, String photoUrl) {
        if (author == null) {
            throw new IllegalArgumentException("`author` must not be null");
        }
        if (photoUrl == null || photoUrl.isBlank()) {
            throw new IllegalArgumentException("`photoUrl` must not be blank");
        }
        this.author = author;
        this.photoUrl = photoUrl;
    }
}
