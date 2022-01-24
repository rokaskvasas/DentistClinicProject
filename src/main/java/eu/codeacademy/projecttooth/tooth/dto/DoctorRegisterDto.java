package eu.codeacademy.projecttooth.tooth.dto;

import eu.codeacademy.projecttooth.tooth.model.modelenum.QualificationEnum;
import eu.codeacademy.projecttooth.tooth.model.modelenum.RoleEnum;
import eu.codeacademy.projecttooth.tooth.model.modelenum.StatusEnum;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorRegisterDto {

    private String firstName;

    private String lastName;

    private String doctorLicense;

    private String password;

    private StatusEnum status;

    private String email;

    private QualificationEnum qualification;

    private Long locationId;

    private String phoneNumber;

    private RoleEnum role;
}
