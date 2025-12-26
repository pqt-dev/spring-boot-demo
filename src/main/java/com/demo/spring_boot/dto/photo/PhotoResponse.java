package com.demo.spring_boot.dto.photo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PhotoResponse(Long id, @JsonProperty("photo_url") String photoUrl,
                            @JsonProperty("uploaded_at") LocalDateTime uploadedAt, Long userId) {
}

