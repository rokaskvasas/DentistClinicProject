package eu.codeacademy.projecttooth.tooth.dto;

import lombok.Data;

@Data
public class EmailSendingRequest {
    private String to, textBody, topic;
}
