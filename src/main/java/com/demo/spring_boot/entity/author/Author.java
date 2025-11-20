package com.demo.spring_boot.entity.author;

import com.demo.spring_boot.entity.photo.Photo;
import com.demo.spring_boot.entity.post.Post;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "authors")
@Data
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String phone;
    @NotBlank(message = "Job is required")
    private String job;

    private String email;

    private String password;

    private String googleId;

    @OneToMany(mappedBy = "author")
    @JsonIgnoreProperties({"author"})
    private List<Post> posts;

    private String avatar;

    @OneToMany(mappedBy = "author")
    @JsonIgnoreProperties({"author"})
    private List<Photo> photos;
}
