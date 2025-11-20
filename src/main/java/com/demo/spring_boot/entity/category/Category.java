package com.demo.spring_boot.entity.category;

import com.demo.spring_boot.entity.post.Post;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    @ManyToMany(mappedBy = "categories")
    @JsonIgnoreProperties({"categories"})
    private List<Post> posts;
}
