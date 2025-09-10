package com.nta.learning.dto.post;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class CreatePostRequest {
    @NotBlank(message = "Title is required")
    private String title;
    private String content;
    private Long authorId;
    private List<Long> categories;

    public List<Long> getCategories() {
        return categories;
    }

    public void setCategories(List<Long> categories) {
        this.categories = categories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
