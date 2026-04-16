package com.ironhack.controller.advice;

import com.ironhack.dto.response.ErrorResponse;
import com.ironhack.dto.response.ValidationErrorResponse;
import com.ironhack.exception.BadRequestException;
import com.ironhack.exception.ConflictException;
import com.ironhack.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidation(MethodArgumentNotValidException exception) {
        Map<String, String> fieldErrors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        error -> Optional.ofNullable(error.getDefaultMessage()).orElse("Validation error"),
                        (existing, replacement) -> existing
                ));

        ValidationErrorResponse response = new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "VALIDATION_ERROR",
                fieldErrors,
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NotFoundException exception) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "NOT_FOUND",
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(ConflictException exception) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "CONFLICT",
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException exception) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "BAD_REQUEST",
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception exception) {
        ErrorResponse response = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_SERVER_ERROR",
                "An unexpected error occurred",
                LocalDateTime.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
