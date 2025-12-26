package com.demo.spring_boot.exception;

import lombok.Getter;

@Getter
public enum ErrorType {
    AUTH_INVALID_CREDENTIALS("Username or password is incorrect"),
    AUTH_EXPIRED_TOKEN("Token has expired"),
    AUTH_FORBIDDEN("Full authentication is required"),
    VALIDATION_ERROR("Validation failed"),
    FILE_SIZE_LIMIT("File size must be less than 500KB"),
    REQUEST_SIZE_LIMIT("Request size must be less than 1MB"),
    RESOURCE_NOT_FOUND("Resource not found"),
    FILE_TYPE_INVALID("Invalid file type"),
    FILE_EMPTY("File is empty");

    private final String defaultMessage;

    ErrorType(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public String getCode() {
        return name();
    }
}

