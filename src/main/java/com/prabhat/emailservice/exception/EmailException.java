package com.prabhat.emailservice.exception;

public class EmailException extends RuntimeException {
    public EmailException(String message) {
        super(message);
    }
}