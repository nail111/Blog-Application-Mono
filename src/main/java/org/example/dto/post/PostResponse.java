package org.example.dto.post;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dto.comment.CommentResponse;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({
        "id",
        "title",
        "description",
        "content",
        "categoryId",
        "comments",
        "createdAt",
        "updatedAt"
})
public class PostResponse {

    @JsonProperty("id")
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_DEFAULT)
    private Long id;
    @JsonProperty("title")
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_DEFAULT)
    private String title;

    @JsonProperty("description")
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_DEFAULT)
    private String description;

    @JsonProperty("content")
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_DEFAULT)
    private String content;

    @JsonProperty("category_id")
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_DEFAULT)
    private Long categoryId;

    @JsonProperty("comments")
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_DEFAULT)
    private Set<CommentResponse> comments = new HashSet<>();

    @JsonProperty("created_at")
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_DEFAULT)
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_DEFAULT)
    private LocalDateTime updatedAt;
}