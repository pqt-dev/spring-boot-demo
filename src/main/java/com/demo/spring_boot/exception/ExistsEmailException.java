package com.demo.spring_boot.exception;

public class ExistsEmailException extends RuntimeException {
    public ExistsEmailException() {
        super("Email already exists");
    }
}
