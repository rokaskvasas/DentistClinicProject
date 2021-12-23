package eu.codeacademy.projecttooth.tooth.advisor;

import eu.codeacademy.projecttooth.tooth.entity.UserEntity;
import eu.codeacademy.projecttooth.tooth.model.User;

public class UserEntityAdvisor {

    public User getUser(UserEntity userEntity) {
        User user = new User();
        user.setUserId(userEntity.getUserId());
        user.setFirstName(userEntity.getFirstName());
        user.setLastName(userEntity.getLastName());
        user.setEmail(userEntity.getEmail());
        user.setRole(userEntity.getRole());
        user.setPhoneNumber(userEntity.getPhoneNumber());
        return user;
    }
}
