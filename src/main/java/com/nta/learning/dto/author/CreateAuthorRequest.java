package com.nta.learning.dto.author;

import jakarta.validation.constraints.NotBlank;

public class CreateAuthorRequest {
    @NotBlank(message = "Name is required")
    public String name;
    @NotBlank(message = "Address is required")
    public String address;
    @NotBlank(message = "Phone is required")
    public String phone;
    @NotBlank(message = "Job is required")
    public String job;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
