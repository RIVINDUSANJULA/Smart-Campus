package com.smartcampus.exception;

public class GlobalExceptionMapper extends RuntimeException {
    public GlobalExceptionMapper(String message) {
        super(message);
    }
}
