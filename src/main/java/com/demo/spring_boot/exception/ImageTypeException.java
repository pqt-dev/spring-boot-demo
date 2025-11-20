package com.demo.spring_boot.exception;

public class ImageTypeException extends IllegalArgumentException {
    public ImageTypeException() {
        super("Invalid file type: only PNG, JPG, JPEG are allowed");
    }
}
