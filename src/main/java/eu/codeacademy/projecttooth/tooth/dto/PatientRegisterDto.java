package eu.codeacademy.projecttooth.tooth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientRegisterDto {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;
}
