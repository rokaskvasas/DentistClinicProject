package eu.codeacademy.projecttooth.tooth.model;

import eu.codeacademy.projecttooth.tooth.model.modelenum.QualificationEnum;
import eu.codeacademy.projecttooth.tooth.model.modelenum.StatusEnum;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Doctor extends User {

    private Long doctorId;

    private Long userId;

    private String doctorLicense;

    private StatusEnum status;

    private QualificationEnum qualification;

    private Long locationId;

}
