package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.dto.UserDto;
import eu.codeacademy.projecttooth.tooth.entity.UserEntity;
import eu.codeacademy.projecttooth.tooth.exception.ObjectNotFoundException;
import eu.codeacademy.projecttooth.tooth.mapper.UserMapper;
import eu.codeacademy.projecttooth.tooth.repository.UserRepository;
import eu.codeacademy.projecttooth.tooth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;


    @Override
    public UserDto getUserByEmail(String email) {
        return mapper.createDtoModel(repository.findByEmail(email).orElseThrow(() -> new ObjectNotFoundException(String.format("User by email: %s not found", email))));
    }

    @Override
    public boolean findIfEmailExist(String email) {
        return repository.findByEmail(email).isPresent();
    }

    @Override
    public List<UserEntity> findAllUsersWithSpecification(Specification<UserEntity> specification) {
        return repository.findAll(specification);
    }

    @Override
    public void deleteUnverifiedUsers(Iterable<UserEntity> expiredUsers) {
        repository.deleteAllInBatch(expiredUsers);
    }


}
