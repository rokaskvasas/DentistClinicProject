package eu.codeacademy.projecttooth.tooth.controller;

import eu.codeacademy.projecttooth.tooth.model.User;
import eu.codeacademy.projecttooth.tooth.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1.0/users")
public class UserController {

    private final UserEntityService userEntityService;

//    @GetMapping()
//    @ResponseStatus(HttpStatus.OK)
//    public List<User> getAllUsers(){
//        return userEntityService.getAllUsers();
//    }
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return  ResponseEntity.ok(userEntityService.getAllUsers());
    }

    @GetMapping("/{Id}")
    public ResponseEntity<User> getUserById(@PathVariable("Id") Long userId){
        return ResponseEntity.ok(userEntityService.getUserById(userId));
    }



//    @GetMapping("/{Id}")
//    @ResponseStatus(HttpStatus.OK)
//    public User getUserById(@PathVariable("Id") Long userId){
//        return userEntityService.getUserById(userId);
//    }

//    @PostMapping()
}
