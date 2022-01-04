package eu.codeacademy.projecttooth.tooth.controller;

import eu.codeacademy.projecttooth.tooth.repository.UserEntityRepository;
import eu.codeacademy.projecttooth.tooth.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserEntityRepository userEntityRepository;


//    @GetMapping
//    public List<UserEntity> getAllUsers() {
//        return userEntityRepository.findAll();
//    }
////
////    @GetMapping("/{id}")
////    public ResponseEntity<User> getUserById(@PathVariable Long id) {
////        log.info("Called method getUserById using" + id);
////        return ResponseEntity.ok(userEntityService.getUserById(id));
////    }


}
