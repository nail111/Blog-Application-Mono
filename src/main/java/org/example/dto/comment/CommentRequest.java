package org.example.dto.comment;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentRequest implements Serializable {

    @NotNull(message = "name can not be null")
    @NotBlank(message = "name can not be empty")
    @Size(min = 3, max = 255, message = "name size is min 3 and max 255")
    private String name;

    @NotNull(message = "email can not be null")
    @NotBlank(message = "email can not be empty")
    @Size(min = 3, max = 255, message = "email size is min 3 and max 255")
    @Email(message = "incorrect email format!")
    private String email;

    @NotNull(message = "body can not be null")
    @NotBlank(message = "body can not be empty")
    @Size(min = 3, max = 255, message = "body size is min 3 and max 255")
    private String body;
}