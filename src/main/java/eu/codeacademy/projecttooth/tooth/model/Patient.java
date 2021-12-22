package eu.codeacademy.projecttooth.tooth.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Patient {

    private Long patientId;

    private Long userId;
}
