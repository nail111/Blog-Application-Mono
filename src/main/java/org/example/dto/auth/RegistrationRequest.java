package org.example.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest implements Serializable {

    @NotNull(message = "name cannot be null")
    @NotEmpty(message = "name cannot be empty")
    private String name;

    @NotNull(message = "username cannot be null")
    @NotEmpty(message = "username cannot be empty")
    private String username;

    @NotNull(message = "email cannot be null")
    @NotEmpty(message = "email cannot be empty")
    @Email(message = "an email should be look like an email")
    private String email;

    @NotNull(message = "password cannot be null")
    @NotEmpty(message = "password cannot be empty")
    private String password;
}