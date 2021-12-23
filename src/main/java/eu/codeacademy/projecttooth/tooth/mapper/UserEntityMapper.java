package eu.codeacademy.projecttooth.tooth.mapper;

import eu.codeacademy.projecttooth.tooth.entity.UserEntity;
import eu.codeacademy.projecttooth.tooth.model.User;
import eu.codeacademy.projecttooth.tooth.model.modelenum.RoleEnum;
import org.springframework.stereotype.Service;

@Service
public class UserEntityMapper {

//    public User getUser(UserEntity userEntity) {
//        User user = new User();
//        user.setUserId(userEntity.getUserId());
//        user.setFirstName(userEntity.getFirstName());
//        user.setLastName(userEntity.getLastName());
//        user.setEmail(userEntity.getEmail());
//        user.setRole(userEntity.getRole());
//        user.setPhoneNumber(userEntity.getPhoneNumber());
//        return user;
//    }

    public UserEntity getUserEntity(User user, RoleEnum role) {
        UserEntity entity = new UserEntity();
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setEmail(user.getEmail());
        entity.setPhoneNumber(user.getPhoneNumber());
        entity.setPassword(user.getPassword());
        entity.setRole(determinateRole(role));
        return entity;
    }

    private String determinateRole(RoleEnum role) {
        switch (role) {
            case ADMIN -> {
                return "ROLE_ADMIN";
            }
            case DOCTOR -> {
                return "ROLE_DOCTOR";
            }
            case PATIENT -> {
                return "ROLE_PATIENT";
            }
        }
        return "ROLE_ANONYMOUS";
    }
}
