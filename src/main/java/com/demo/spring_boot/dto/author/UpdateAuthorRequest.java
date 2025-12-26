package com.demo.spring_boot.dto.author;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAuthorRequest {
    public String name;
    public String address;
    public String phone;
    public String job;
    public String avatar;
}
