package eu.codeacademy.projecttooth.tooth.model;


import eu.codeacademy.projecttooth.tooth.validation.ValidEmail;
import eu.codeacademy.projecttooth.tooth.validation.ValidOnlyAlphabets;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
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
    private String firstName;

    @NotBlank
    @Size(min = 2, max = 30)
    @ValidOnlyAlphabets
    private String lastName;

    @ValidEmail
    private String email;

    @NotBlank
    @Size(min = 8, max = 10)
    private String phoneNumber;
    @NotBlank
    @Size(min = 4, max = 30)
    private String password;

    private String role;

}
