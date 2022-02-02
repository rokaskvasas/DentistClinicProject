package eu.codeacademy.projecttooth.tooth.service;


import eu.codeacademy.projecttooth.tooth.dto.UserDto;
import eu.codeacademy.projecttooth.tooth.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;


public interface UserService {

    UserDto getUserByEmail(String email);

    boolean findIfEmailExist(String email);

    List<UserEntity> findAllUsersWithSpecification(Specification<UserEntity> specification);

    void deleteUnverifiedUsers(Iterable<UserEntity> expiredUsers);
}
