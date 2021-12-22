package eu.codeacademy.projecttooth.tooth.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    private Long doctorId;

    private Long userId;

    private String doctorLicense;

    private StatusEnum status;

    private QualificationEnum qualification;

    private Long locationId;
}
