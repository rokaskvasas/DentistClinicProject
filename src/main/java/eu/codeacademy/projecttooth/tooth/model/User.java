package eu.codeacademy.projecttooth.tooth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public abstract class User {

    private Long userId;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    @JsonIgnore
    private String password;

    private String role;

}
