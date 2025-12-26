package com.demo.spring_boot.entity.author;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "authors")
@Getter
@Setter
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String job;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String googleId;

    private String avatar;
}
