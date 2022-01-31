package eu.codeacademy.projecttooth.tooth.dto;

import eu.codeacademy.projecttooth.tooth.model.modelenum.QualificationEnum;
import eu.codeacademy.projecttooth.tooth.validation.ValidOnlyAlphabets;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {


    private Long doctorId;

    @NotBlank
    @Size(min = 2, max = 30)
    @ValidOnlyAlphabets
    @NotNull
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 30)
    @ValidOnlyAlphabets
    @NotNull
    private String lastName;

    @Pattern(regexp = "^(\\+3706)?\\d{7}$")
    private String phoneNumber;

    @NotBlank
    @Size(min = 5, max = 70)
    private String doctorLicense;

    @NotNull
    private QualificationEnum qualification;


}
