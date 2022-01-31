package eu.codeacademy.projecttooth.tooth.model;


import eu.codeacademy.projecttooth.tooth.validation.ValidEmail;
import eu.codeacademy.projecttooth.tooth.validation.ValidOnlyAlphabets;
import eu.codeacademy.projecttooth.tooth.validation.ValidPassword;
import eu.codeacademy.projecttooth.tooth.validation.ValidPhoneNumber;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public abstract class User {

    private Long userId;

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

    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;

    @NotBlank
    @ValidPhoneNumber
    @NotNull
    private String phoneNumber;

    @NotBlank
    @ValidPassword
    @NotNull
    private String password;

    private String role;

}
