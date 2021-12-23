package eu.codeacademy.projecttooth.tooth.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ErrorResponse {

    private Instant timestamp;
    private String message;

    public ErrorResponse() {
        this.timestamp = Instant.now();
    }

    public ErrorResponse(String message) {
        this();
        this.message = message;
    }
}
