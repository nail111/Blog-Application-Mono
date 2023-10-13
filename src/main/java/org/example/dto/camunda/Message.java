package org.example.dto.camunda;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.io.Serializable;

@JsonPropertyOrder({
        "code",
        "type",
        "message",
})
@Data
public class Message implements Serializable {
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_DEFAULT)
    private int code;
    @JsonProperty("type")
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_DEFAULT)
    private String type;
    @JsonProperty("message")
    @JsonInclude(value = JsonInclude.Include.NON_NULL, content = JsonInclude.Include.NON_DEFAULT)
    private String message;

    public Message() {
    }

    public Message(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Message(String type, String message) {
        this.code = 200;
        this.type = type;
        this.message = message;
    }

    public Message(int code, String type, String message) {
        this.code = code;
        this.type = type;
        this.message = message;
    }
}