package eu.codeacademy.projecttooth.tooth.email;

import lombok.Data;

@Data
public class EmailSendingRequest {
    private String to, textBody, topic;
}
