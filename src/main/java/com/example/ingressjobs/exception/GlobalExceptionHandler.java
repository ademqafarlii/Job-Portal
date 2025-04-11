package com.example.ingressjobs.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JobNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleJobNotFoundException(JobNotFoundException e, WebRequest webRequest) {
        Map<String, String> errors = new HashMap<>();

        errors.put("message", e.getMessage());

        log.error("Job not found exception", e);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(errors)
                .path(webRequest.getHeader("Content-Type"))
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDate.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyInUseException(EmailAlreadyInUseException e, WebRequest webRequest) {
        Map<String, String> errors = new HashMap<>();

        errors.put("message", e.getMessage());

        log.error("Email already in use exception", e);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(errors)
                .path(webRequest.getHeader("Content-Type"))
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDate.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }



    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException e,
                                                                      WebRequest webRequest) {

        Map<String, String> errors = new HashMap<>();
        errors.put("message", e.getMessage());

        log.error("No such element exception", e);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(errors)
                .path(webRequest.getHeader("Content-Type"))
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDate.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
                                                                               WebRequest webRequest) {

        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(fieldError ->
                errors.put(fieldError.getField(), fieldError.getDefaultMessage()));

        log.error("Field is not valid : {}", errors);

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(errors)
                .path(webRequest.getHeader("Content-Type"))
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDate.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

}
