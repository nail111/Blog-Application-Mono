package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({
        "title",
        "description",
        "content",
        "createdAt",
        "updatedAt"
})
public class PostResponse {
    @JsonProperty("title")
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_DEFAULT)
    private String title;

    @JsonProperty("description")
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_DEFAULT)
    private String description;

    @JsonProperty("content")
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_DEFAULT)
    private String content;

    @JsonProperty("created_at")
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_DEFAULT)
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_DEFAULT)
    private LocalDateTime updatedAt;
}