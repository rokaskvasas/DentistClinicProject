package eu.codeacademy.projecttooth.tooth.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.codeacademy.projecttooth.tooth.validation.ValidEmail;
import eu.codeacademy.projecttooth.tooth.validation.ValidOnlyAlphabets;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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
    @Pattern(regexp = "^[A-Za-z]*$", message = "Please use only alphabets")
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
