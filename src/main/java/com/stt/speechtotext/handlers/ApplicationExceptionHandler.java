package com.stt.speechtotext.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApplicationExceptionHandler {

    /*
    This handler will intercept MaxUploadSizeExceededException and add custom error message.
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Map<String, String>> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException exc) {
        Map<String, String> errorResponse = new HashMap<>();
        // Customize your error message here
        errorResponse.put("timestamp", String.valueOf(System.currentTimeMillis()));
        errorResponse.put("status", HttpStatus.PAYLOAD_TOO_LARGE.toString());
        errorResponse.put("message", "File upload size exceeded the allowed limit (e.g., 1MB). Please choose a smaller file.");

        return new ResponseEntity<>(errorResponse, HttpStatus.PAYLOAD_TOO_LARGE);
    }

    // Add other exception handlers as needed
}