package com.demo.spring_boot.dto.author;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAuthorRequest {
    @NotBlank(message = "Name is required")
    public String name;
    @NotBlank(message = "Address is required")
    public String address;
    @NotBlank(message = "Phone is required")
    public String phone;
    @NotBlank(message = "Job is required")
    public String job;
    @NotBlank(message = "Email is required")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
}
