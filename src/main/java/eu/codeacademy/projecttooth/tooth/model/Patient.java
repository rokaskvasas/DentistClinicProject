package eu.codeacademy.projecttooth.tooth.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Patient extends User {

    private Long patientId;

}
