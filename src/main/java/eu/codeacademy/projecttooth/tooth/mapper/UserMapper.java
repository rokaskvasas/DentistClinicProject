package eu.codeacademy.projecttooth.tooth.mapper;

import eu.codeacademy.projecttooth.tooth.dto.UserDto;
import eu.codeacademy.projecttooth.tooth.entity.UserEntity;
import eu.codeacademy.projecttooth.tooth.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {

    public UserEntity getUserEntity(User user) {
        return UserEntity.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .build();
    }

    public UserDto createDtoModel(UserEntity entity){
        return UserDto.builder()
                .email(entity.getEmail())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .phoneNumber(entity.getPhoneNumber())
                .role(entity.getRole())
                .userId(entity.getUserId())
                .build();
    }

}
