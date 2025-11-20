package com.demo.spring_boot.dto.author;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateAuthorRequest {
    @NotBlank(message = "Name is required")
    public String name;
    @NotBlank(message = "Address is required")
    public String address;
    @NotBlank(message = "Phone is required")
    public String phone;
    @NotBlank(message = "Job is required")
    public String job;
    private String email;
    private String password;
}
