package com.demo.spring_boot.dto.post;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UpdatePostRequest {
    private String title;
    private String content;
    private List<Long> categories;
}
