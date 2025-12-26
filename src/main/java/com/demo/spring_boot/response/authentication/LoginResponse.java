package com.demo.spring_boot.response.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private final String type = "Bearer";
    private String token;
    private String email;
    private String name;
    private Long expiresIn; // seconds
}
