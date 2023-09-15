package org.example.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        List<String> error = methodArgumentNotValidException
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        return new ResponseEntity<>(error, BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException dataIntegrityViolationException) {
        String errorMessage = dataIntegrityViolationException.getMessage();
        String errorResponse = null;
        if (errorMessage.contains("Duplicate entry")) {
            Pattern pattern = Pattern.compile("Duplicate entry '([^']+)' for key '([^']+)'");
            Matcher matcher = pattern.matcher(errorMessage);
            if (matcher.find()) {
                errorResponse = matcher.group();
            }
        }
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }
}