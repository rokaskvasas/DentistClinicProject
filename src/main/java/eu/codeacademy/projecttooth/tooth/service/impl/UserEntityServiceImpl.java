package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.advisor.UserEntityAdvisor;
import eu.codeacademy.projecttooth.tooth.entity.UserEntity;
import eu.codeacademy.projecttooth.tooth.model.User;
import eu.codeacademy.projecttooth.tooth.repository.UserEntityRepository;
import eu.codeacademy.projecttooth.tooth.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserEntityServiceImpl implements UserEntityService {

    private final UserEntityRepository userEntityRepository;

    private final UserEntityAdvisor userEntityAdvisor;

    @Override
    public List<User> getAllUsers() {
        return userEntityRepository.findAll().stream().map(this::createUserFromEntity).collect(Collectors.toUnmodifiableList());
    }

    private User createUserFromEntity(UserEntity userEntity){
        return userEntityAdvisor.getUser(userEntity);
    }

}
