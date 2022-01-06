package eu.codeacademy.projecttooth.tooth.dto;

import eu.codeacademy.projecttooth.tooth.model.modelenum.QualificationEnum;
import eu.codeacademy.projecttooth.tooth.model.modelenum.StatusEnum;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class DoctorDto {

    private String firstName;

    private String lastName;

    private String doctorLicense;

    private StatusEnum status;

    private QualificationEnum qualification;

    private String locationCity;

    private String locationName;

    private String phoneNumber;
}
