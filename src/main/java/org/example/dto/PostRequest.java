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

    @NotNull(message = "title can not be null")
    @NotBlank(message = "title can not be empty")
    @Size(min = 3, max = 255, message = "title size is min 3 and max 255")
    private String title;

    @NotNull(message = "description can not be null")
    @NotBlank(message = "description can not be empty")
    @Size(min = 3, max = 255, message = "description size is min 3 and max 255")
    private String description;

    @NotNull(message = "content can not be null")
    @NotBlank(message = "content can not be empty")
    @Size(min = 3, max = 255, message = "content size is min 3 and max 255")
    private String content;
}