package org.example.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.example.dto.error.CustomErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handle(MethodArgumentNotValidException methodArgumentNotValidException) {
        List<String> error = methodArgumentNotValidException
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();

        return new ResponseEntity<>(error, BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handle(DataIntegrityViolationException dataIntegrityViolationException) {
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

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handle(ResourceNotFoundException resourceNotFoundException) {
        return new ResponseEntity<>(resourceNotFoundException.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomErrorResponse> handle(AccessDeniedException accessDeniedException, WebRequest webRequest) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                LocalDateTime.now(),
                "Access Denied",
                HttpStatus.FORBIDDEN.value(),
                webRequest.getDescription(false)
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<CustomErrorResponse> handle(AuthenticationException authenticationException, WebRequest webRequest) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                LocalDateTime.now(),
                "username (or email) or password incorrect",
                BAD_REQUEST.value(),
                webRequest.getDescription(false)
        );

        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<CustomErrorResponse> handle(RegistrationException registrationException, WebRequest webRequest) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                LocalDateTime.now(),
                registrationException.getMessage(),
                BAD_REQUEST.value(),
                webRequest.getDescription(false)
        );

        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<CustomErrorResponse> handle(MalformedJwtException malformedJwtException, WebRequest webRequest) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                LocalDateTime.now(),
                "INVALID TOKEN",
                BAD_REQUEST.value(),
                webRequest.getDescription(false)
        );

        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<CustomErrorResponse> handle(ExpiredJwtException expiredJwtException, WebRequest webRequest) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                LocalDateTime.now(),
                "TOKEN EXPIRED",
                BAD_REQUEST.value(),
                webRequest.getDescription(false)
        );

        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ResponseEntity<CustomErrorResponse> handle(UnsupportedJwtException unsupportedJwtException, WebRequest webRequest) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                LocalDateTime.now(),
                "UNSUPPORTED TOKEN",
                BAD_REQUEST.value(),
                webRequest.getDescription(false)
        );

        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomErrorResponse> handle(IllegalArgumentException illegalArgumentException, WebRequest webRequest) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                LocalDateTime.now(),
                illegalArgumentException.getMessage(),
                BAD_REQUEST.value(),
                webRequest.getDescription(false)
        );

        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handle(Exception exception, WebRequest webRequest) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                LocalDateTime.now(),
                exception.getMessage(),
                INTERNAL_SERVER_ERROR.value(),
                webRequest.getDescription(false)
        );

        return new ResponseEntity<>(errorResponse, INTERNAL_SERVER_ERROR);
    }
}