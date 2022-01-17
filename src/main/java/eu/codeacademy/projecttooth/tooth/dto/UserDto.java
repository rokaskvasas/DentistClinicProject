package eu.codeacademy.projecttooth.tooth.dto;

import lombok.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long userId;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String password;

    private String role;
}
