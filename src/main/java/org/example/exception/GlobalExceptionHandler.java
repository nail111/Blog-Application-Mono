package org.example.exception;

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

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        return new ResponseEntity<>(resourceNotFoundException.getMessage(), BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomErrorResponse> handleAccessDeniedException(
            AccessDeniedException accessDeniedException,
            WebRequest webRequest
    ) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                LocalDateTime.now(),
                "Access Denied",
                HttpStatus.FORBIDDEN.value(),
                webRequest.getDescription(false)
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<CustomErrorResponse> handleAuthenticationException(
            AuthenticationException authenticationException,
            WebRequest webRequest
    ) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                LocalDateTime.now(),
                "username (or email) or password incorrect",
                BAD_REQUEST.value(),
                webRequest.getDescription(false)
        );

        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<CustomErrorResponse> handleRegistrationException(
            RegistrationException registrationException,
            WebRequest webRequest
    ) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                LocalDateTime.now(),
                registrationException.getMessage(),
                BAD_REQUEST.value(),
                webRequest.getDescription(false)
        );

        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<CustomErrorResponse> handleJwtException(JwtException jwtException, WebRequest webRequest) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(
                LocalDateTime.now(),
                jwtException.getMessage(),
                BAD_REQUEST.value(),
                webRequest.getDescription(false)
        );

        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }
}