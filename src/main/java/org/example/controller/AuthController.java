package org.example.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.auth.LoginRequest;
import org.example.dto.auth.RegistrationRequest;
import org.example.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping(value = {"/registration", "/signup"})
    public ResponseEntity<String> registartion(@RequestBody @Valid RegistrationRequest registrationRequest) {
        return new ResponseEntity<>(authService.registration(registrationRequest), HttpStatus.CREATED);
    }
}