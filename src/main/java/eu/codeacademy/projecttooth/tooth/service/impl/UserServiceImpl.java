package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.dto.UserDto;
import eu.codeacademy.projecttooth.tooth.entity.UserEntity;
import eu.codeacademy.projecttooth.tooth.exception.ObjectNotFoundException;
import eu.codeacademy.projecttooth.tooth.mapper.UserMapper;
import eu.codeacademy.projecttooth.tooth.repository.UserRepository;
import eu.codeacademy.projecttooth.tooth.service.UserService;
import eu.codeacademy.projecttooth.tooth.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final UserSpecification userSpecification;


    @Override
    public UserDto getUserByEmail(String email) {
        return mapper.createDtoModel(repository.findByEmail(email).orElseThrow(() -> new ObjectNotFoundException(String.format("User by email: %s not found", email))));
    }

    @Override
    public void deleteUnverifiedUsers() {

        repository.deleteAllById(unverifiedUsersId());
    }

    private Iterable<Long> unverifiedUsersId() {
        return () -> repository.findAll(userSpecification.findUnverifiedUsers()).stream().mapToLong(UserEntity::getUserId).iterator();
    }

}
