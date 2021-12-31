package eu.codeacademy.projecttooth.tooth.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class Patient extends User {

    private Long patientId;

}
