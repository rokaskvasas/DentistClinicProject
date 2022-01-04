package eu.codeacademy.projecttooth.tooth.mapper;

import eu.codeacademy.projecttooth.tooth.entity.UserEntity;
import eu.codeacademy.projecttooth.tooth.model.User;
import eu.codeacademy.projecttooth.tooth.model.modelenum.RoleEnum;
import eu.codeacademy.projecttooth.tooth.security.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordService passwordService;

    public UserEntity getUserEntity(User user, RoleEnum role) {
        return UserEntity.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .password(passwordService.passwordEncoder().encode(user.getPassword()))
                .role(role.determinateRole())
                .build();
    }

}
