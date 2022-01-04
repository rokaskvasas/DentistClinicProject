package eu.codeacademy.projecttooth.tooth.controller;

import eu.codeacademy.projecttooth.tooth.model.Patient;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;
import eu.codeacademy.projecttooth.tooth.service.PatientEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Secured({"ROLE_PATIENT","ROLE_ADMIN"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {

    private final PatientEntityService service;

    @GetMapping
    public Patient getPatient(@AuthenticationPrincipal UserPrincipal userPrincipal){
        return service.getPatient(userPrincipal.getUserId());
    }


    @PreAuthorize("permitAll()")
    @PostMapping
    public void createPatient(@RequestBody Patient patient) {
        service.createPatient(patient);
    }

}
