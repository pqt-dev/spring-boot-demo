package com.demo.spring_boot.response;

import lombok.Builder;

import java.util.List;

@Builder
public record DetailsErrorResponse(List<String> details) {
}
