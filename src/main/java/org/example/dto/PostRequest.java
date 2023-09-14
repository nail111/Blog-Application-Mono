package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostRequest {

    @NotNull
    @NotBlank
    @Size(min = 3, max = 255)
    private String title;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 255)
    private String description;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 255)
    private String content;
}