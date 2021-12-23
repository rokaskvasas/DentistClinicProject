package eu.codeacademy.projecttooth.tooth.model;

import lombok.*;

@Getter
@Setter
public abstract class User {

    private Long userId;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String password;

    private String role;

}
