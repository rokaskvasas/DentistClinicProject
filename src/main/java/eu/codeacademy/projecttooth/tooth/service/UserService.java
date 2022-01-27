package eu.codeacademy.projecttooth.tooth.service;


import eu.codeacademy.projecttooth.tooth.dto.UserDto;


public interface UserService {

    UserDto getUserByEmail(String email);

    void deleteUnverifiedUsers();
}
