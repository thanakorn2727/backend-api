package com.thanakornget.backendjavaapi.exception;

import com.thanakornget.backendjavaapi.model.response.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse<Map<String, Object>>> handleResourceNotFound(
            ResourceNotFoundException exception) {
        APIResponse<Map<String, Object>> response = new APIResponse<>(
                false,
                exception.getMessage(),
                Map.of()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
