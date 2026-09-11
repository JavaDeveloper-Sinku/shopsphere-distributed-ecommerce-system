package com.example.orderservice.exception;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponse {

    private int status;
    private String message;
    private LocalDateTime timestamp;
    private Map<String, String> errors;

    // Constructor for normal exceptions
    // Example: Order not found
    public ErrorResponse(
            int status,
            String message,
            LocalDateTime timestamp) {

        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    // Constructor for validation exceptions
    // Example: quantity validation failed
    public ErrorResponse(
            int status,
            String message,
            LocalDateTime timestamp,
            Map<String, String> errors) {

        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        this.errors = errors;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}