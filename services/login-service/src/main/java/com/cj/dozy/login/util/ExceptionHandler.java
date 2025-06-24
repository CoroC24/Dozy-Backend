package com.cj.dozy.login.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        return buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST, request.getRequestURI());
    }

    private ResponseEntity<Object> buildErrorResponse(String message, HttpStatus stauts, String path) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", stauts);
        body.put("error", stauts.getReasonPhrase());
        body.put("message", message);
        body.put("path", path);

        return new ResponseEntity<>(body, stauts);
    }
}
