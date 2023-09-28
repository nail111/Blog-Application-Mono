package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.auth.JWTAuthResponse;
import org.example.dto.auth.LoginRequest;
import org.example.dto.auth.RegistrationRequest;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.exception.RegistrationException;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.example.security.JwtTokenProvider;
import org.example.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public JWTAuthResponse login(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return jwtAuthResponse;
    }

    @Override
    public String registration(RegistrationRequest registrationRequest) {

        validateUniqueFields(registrationRequest.getUsername(), registrationRequest.getEmail());

        User user = createUserFromRequest(registrationRequest);
        userRepository.save(user);

        return "User registered successfully!!!";
    }

    private void validateUniqueFields(String username, String email) {
        if (userRepository.existsByUsername(username)) {
            throw new RegistrationException("This username is already taken");
        }

        if (userRepository.existsByEmail(email)) {
            throw new RegistrationException("This email is already taken");
        }
    }

    private User createUserFromRequest(RegistrationRequest registrationRequest) {
        User user = new User();
        user.setName(registrationRequest.getName());
        user.setUsername(registrationRequest.getUsername());
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new RuntimeException("Default role not found"));
        roles.add(userRole);

        user.setRoles(roles);
        return user;
    }
}