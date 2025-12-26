package com.demo.spring_boot.dto.post;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponse {
    private Long id;
    private String title;
    private String content;
}
