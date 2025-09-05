package com.cj.dozy.login.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request) {
        return buildErrorResponse(e.getMessage(), request.getRequestURI(), Arrays.toString(e.getStackTrace()));
    }

    private ResponseEntity<Object> buildErrorResponse(String message, String path, String exceptionInfo) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
        body.put("message", message);
        body.put("path", path);
        body.put("exceptionInfo", exceptionInfo);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
