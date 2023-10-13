package org.example.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest implements Serializable {

    @NotNull(message = "name can not be null")
    @NotBlank(message = "name can not be empty")
    @Size(min = 3, max = 255, message = "name size is min 3 and max 255")
    private String name;

    @NotNull(message = "description can not be null")
    @NotBlank(message = "description can not be empty")
    @Size(min = 3, max = 255, message = "description size is min 3 and max 255")
    private String description;
}