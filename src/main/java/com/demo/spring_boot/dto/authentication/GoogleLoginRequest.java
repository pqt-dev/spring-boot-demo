package com.demo.spring_boot.dto.authentication;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

public class GoogleLoginRequest {
    @JsonProperty("id_token_string")
    @Getter
    @Setter
    private String idTokenString;
}
