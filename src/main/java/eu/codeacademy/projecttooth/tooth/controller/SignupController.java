package eu.codeacademy.projecttooth.tooth.controller;

import eu.codeacademy.projecttooth.tooth.model.Doctor;
import eu.codeacademy.projecttooth.tooth.model.Patient;
import eu.codeacademy.projecttooth.tooth.model.User;
import eu.codeacademy.projecttooth.tooth.repository.DoctorEntityRepository;
import eu.codeacademy.projecttooth.tooth.repository.PatientEntityRepository;
import eu.codeacademy.projecttooth.tooth.repository.UserEntityRepository;
import eu.codeacademy.projecttooth.tooth.service.DoctorEntityService;
import eu.codeacademy.projecttooth.tooth.service.PatientEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/signup")
@Slf4j
public class SignupController {


    private final PatientEntityService patientEntityService;



//    @GetMapping


    //    @PostMapping("/")
//    public ResponseEntity<?> createUser(@RequestPart User user, @RequestPart Patient patient, @RequestPart Doctor doctor){
//
//    }


}
