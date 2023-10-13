package org.example.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(
        description = "PostRequest Model Information"
)
public class PostRequest implements Serializable {

    @NotNull(message = "title can not be null")
    @NotBlank(message = "title can not be empty")
    @Size(min = 3, max = 255, message = "title size is min 3 and max 255")
    @Schema(description = "Blog Post title")
    private String title;

    @NotNull(message = "description can not be null")
    @NotBlank(message = "description can not be empty")
    @Size(min = 3, max = 255, message = "description size is min 3 and max 255")
    @Schema(description = "Blog Post description")
    private String description;

    @NotNull(message = "content can not be null")
    @NotBlank(message = "content can not be empty")
    @Size(min = 3, max = 255, message = "content size is min 3 and max 255")
    @Schema(description = "Blog Post content")
    private String content;

    @NotNull(message = "categoryId can not be null")
    @Schema(description = "Blog Post categoryId")
    private Long categoryId;
}