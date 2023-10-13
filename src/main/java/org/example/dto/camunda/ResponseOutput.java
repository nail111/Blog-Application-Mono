package org.example.dto.camunda;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Collections;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "uid",
        "timestamp",
        "status",
        "messages"
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseOutput<C> implements Serializable {
    @JsonProperty("uid")
    private String uid;
    @JsonProperty("timestamp")
    private BigInteger timestamp;
    @JsonProperty("status")
    private Integer status;
    @JsonProperty("messages")
    private List<Message> messages = null;

    public static ResponseEntity<ResponseOutput> success(String uuid, String message) {
        return ResponseEntity.ok(new ResponseOutput<>(uuid, BigInteger.valueOf(Instant.now().getEpochSecond()), 200,
                Collections.singletonList(new Message(200, message, "CONFIRMED"))));
    }

    public static ResponseEntity<ResponseOutput> error(HttpStatus httpStatus, String uuid, Integer status, String globalErrorCode, String message) {
        return ResponseEntity.status(httpStatus).body(new ResponseOutput<>(uuid, BigInteger.valueOf(Instant.now().getEpochSecond()), status,
                Collections.singletonList(new Message(status, message, globalErrorCode))));
    }
}