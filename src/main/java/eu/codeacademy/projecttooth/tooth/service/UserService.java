package eu.codeacademy.projecttooth.tooth.service;


import eu.codeacademy.projecttooth.tooth.dto.UserDto;
import eu.codeacademy.projecttooth.tooth.model.User;
import org.springframework.stereotype.Service;


public interface UserService {

    UserDto getUserByEmail(String email);
}
