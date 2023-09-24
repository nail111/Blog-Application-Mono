package org.example.dto.comment;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonPropertyOrder({
        "id",
        "name",
        "email",
        "body",
        "postId",
        "createdAt",
        "updatedAt"
})
public class CommentResponse implements Serializable {

    @JsonProperty("id")
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_DEFAULT)
    private Long id;

    @JsonProperty("name")
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_DEFAULT)
    private String name;

    @JsonProperty("email")
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_DEFAULT)
    private String email;

    @JsonProperty("body")
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_DEFAULT)
    private String body;

    @JsonProperty("post_id")
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_DEFAULT)
    private Long postId;

    @JsonProperty("created_at")
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_DEFAULT)
    private LocalDateTime createdAt;

    @JsonProperty("updated_at")
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_DEFAULT)
    private LocalDateTime updatedAt;
}