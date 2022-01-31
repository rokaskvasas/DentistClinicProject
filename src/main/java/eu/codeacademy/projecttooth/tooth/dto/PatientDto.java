package eu.codeacademy.projecttooth.tooth.dto;

import eu.codeacademy.projecttooth.tooth.validation.ValidOnlyAlphabets;
import eu.codeacademy.projecttooth.tooth.validation.ValidPhoneNumber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto{

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

    @ValidPhoneNumber
    private String phoneNumber;
}
