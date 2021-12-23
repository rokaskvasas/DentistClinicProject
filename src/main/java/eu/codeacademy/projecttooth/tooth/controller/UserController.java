package eu.codeacademy.projecttooth.tooth.controller;

import eu.codeacademy.projecttooth.tooth.model.User;
import eu.codeacademy.projecttooth.tooth.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/users")
public class UserController {

    private final UserEntityService userEntityService;


    @GetMapping
    public List<User> getAllUsers() {
        return userEntityService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        log.info("Called method getUserById using" + id);
        return ResponseEntity.ok(userEntityService.getUserById(id));
    }


}
