package com.demo.spring_boot.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, Object id) {
        super(resourceName + " with id " + id + " not found");
    }
}
