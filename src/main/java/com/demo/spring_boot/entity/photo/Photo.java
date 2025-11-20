package com.demo.spring_boot.entity.photo;

import com.demo.spring_boot.entity.author.Author;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "author_photos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String photoUrl;
    @Column(name = "uploaded_at", insertable = false, updatable = false)
    private LocalDateTime uploadedAt;


    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonIgnoreProperties({"photos"})
    private Author author;

    private Boolean isLocal;
}
