package com.demo.spring_boot.dto.author;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorResponse {
    private Long id;
    private String name;
    private String address;
    private String phone;
    private String job;
    private String email;
    private String googleId;
    private String avatar;
}
