package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.mapper.UserEntityMapper;
import eu.codeacademy.projecttooth.tooth.entity.UserEntity;
import eu.codeacademy.projecttooth.tooth.exception.UserByIdNotFoundException;
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

//    private final UserEntityRepository userEntityRepository;
//
//    private final UserEntityMapper userEntityMapper;
//
//    @Override
//    public List<User> getAllUsers() {
//        return userEntityRepository.findAll().stream().map(this::createUserFromEntity).collect(Collectors.toUnmodifiableList());
//    }
//
//    @Override
//    public User getUserById(Long userId) {
//        return createUserFromEntity(userEntityRepository.findById(userId).orElseThrow(() -> new UserByIdNotFoundException("User not found:" + userId)));
//    }
////
////    private User createUserFromEntity(UserEntity userEntity) {
////        return userEntityMapper.getUser(userEntity);
////    }
////

}
