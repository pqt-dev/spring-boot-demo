package com.demo.spring_boot.entity.category;

import com.demo.spring_boot.entity.post.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ManyToMany(mappedBy = "categories")
    private List<Post> posts;

    public Category(String name, String description) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("`name` must not be blank");
        }
        this.name = name;
        this.description = description;
    }

    public void update(String name, String description) {
        if (name != null) {
            this.name = name;
        }
        if (description != null) {
            this.description = description;
        }
    }


}
