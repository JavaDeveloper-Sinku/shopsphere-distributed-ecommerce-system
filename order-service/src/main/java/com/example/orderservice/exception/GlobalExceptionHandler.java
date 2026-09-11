package com.example.orderservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleOrderNotFound(
            OrderNotFoundException ex) {

        ErrorResponse response = new ErrorResponse(
                404,
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(404)
                .body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage,
                        (existing, replacement) -> existing
                ));

        ErrorResponse response = new ErrorResponse(
                400,
                "Validation failed",
                LocalDateTime.now(),
                errors
        );

        return ResponseEntity
                .status(400)
                .body(response);
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFound(
            ProductNotFoundException ex) {

        ErrorResponse response = new ErrorResponse(
                404,
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(404)
                .body(response);
    }
    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<ErrorResponse> handleOutOfStock(
            OutOfStockException ex) {

        ErrorResponse response = new ErrorResponse(
                400,
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(400)
                .body(response);
    }
}