package eu.codeacademy.projecttooth.tooth.dto;

import eu.codeacademy.projecttooth.tooth.model.modelenum.QualificationEnum;
import eu.codeacademy.projecttooth.tooth.model.modelenum.StatusEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {

    private Long doctorId;

    private String firstName;

    private String lastName;

    private String doctorLicense;

    private StatusEnum status;

    private QualificationEnum qualification;

    private String locationCity;

    private String locationName;

    private String phoneNumber;
}
