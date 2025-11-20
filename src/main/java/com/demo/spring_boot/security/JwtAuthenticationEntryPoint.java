package com.demo.spring_boot.security;

import com.demo.spring_boot.exception.ErrorType;
import com.demo.spring_boot.response.ApiResponse;
import com.demo.spring_boot.response.DetailsErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public JwtAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        final var error = ApiResponse.<DetailsErrorResponse>builder()
                                     .data(DetailsErrorResponse.builder()
                                                               .details(
                                                                       List.of(ErrorType.AUTH_FORBIDDEN.getDetails()))
                                                               .build())
                                     .code(ErrorType.AUTH_FORBIDDEN.getCode())
                                     .message(authException.getMessage())
                                     .build();
        objectMapper.writeValue(response.getOutputStream(), error);
    }
}