package eu.codeacademy.projecttooth.tooth.dto;

import eu.codeacademy.projecttooth.tooth.model.Location;
import eu.codeacademy.projecttooth.tooth.model.modelenum.QualificationEnum;
import eu.codeacademy.projecttooth.tooth.model.modelenum.StatusEnum;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorRegisterDto {

    private Long doctorId;

    private Long userId;

    private String firstName;

    private String lastName;

    private String doctorLicense;

    private StatusEnum status;

    private String email;

    private QualificationEnum qualification;

    private Location location;

    private String phoneNumber;

}
