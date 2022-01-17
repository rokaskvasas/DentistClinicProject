package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.dto.UserDto;
import eu.codeacademy.projecttooth.tooth.exception.ObjectNotFoundException;
import eu.codeacademy.projecttooth.tooth.mapper.UserMapper;
import eu.codeacademy.projecttooth.tooth.model.User;
import eu.codeacademy.projecttooth.tooth.repository.UserRepository;
import eu.codeacademy.projecttooth.tooth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public UserDto getUserByEmail(String email){
        return mapper.createDtoModel(repository.findByEmail(email).orElseThrow(() -> new ObjectNotFoundException(String.format("User by email: %s not found", email))));
    }

}
