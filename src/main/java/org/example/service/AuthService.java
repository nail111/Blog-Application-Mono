package org.example.service;

import org.example.dto.auth.JWTAuthResponse;
import org.example.dto.auth.LoginRequest;
import org.example.dto.auth.RegistrationRequest;

public interface AuthService {
    JWTAuthResponse login(LoginRequest loginRequest);

    String registration(RegistrationRequest registrationRequest);
}
