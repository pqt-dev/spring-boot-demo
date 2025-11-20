package com.demo.spring_boot.exception;

import lombok.Getter;

@Getter
public enum ErrorType {
    AUTH_INVALID_CREDENTIALS("AUTH_INVALID_CREDENTIALS", "Username or password is incorrect"),
    AUTH_EXPIRED_TOKEN("AUTH_EXPIRED_TOKEN", "Token has expired"),
    AUTH_FORBIDDEN("AUTH_FORBIDDEN", "Full authentication is required to access this resource"),
    VALIDATION_ERROR("VALIDATION_ERROR", "Validation failed"),
    MAX_UPLOAD_SIZE("MAX_UPLOAD_SIZE", "File size must be less than 10MB!"),
    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND", "Resource not found"),
    FILE_TYPE_INVALID("FILE_TYPE_INVALID", "Invalid file type: only PNG, JPG, JPEG are allowed!"),
    FILE_EMPTY("FILE_EMPTY", "File is empty"),
    ;

    private final String code;
    private final String details;

    ErrorType(String code, String details) {
        this.code = code;
        this.details = details;
    }

}
