package eu.codeacademy.projecttooth.tooth.controller;

import eu.codeacademy.projecttooth.tooth.model.Patient;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;
import eu.codeacademy.projecttooth.tooth.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Secured({"ROLE_PATIENT", "ROLE_ADMIN"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {

    private final PatientService service;

    @GetMapping("/account")
    public Patient getPatient(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return service.getPatient(userPrincipal.getUserId());
    }

    @PreAuthorize("permitAll()")
    @PostMapping
    public Patient createPatient(@Valid @RequestBody Patient patient) {
        return service.createPatient(patient);
    }

    @PutMapping
    public Patient updatePatient(@AuthenticationPrincipal UserPrincipal principal, @RequestBody Patient patient) {
        return service.updatePatient(patient, principal.getUserId());
    }

    @DeleteMapping("{userId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') || #userId == principal.getUserId()")
    public void deletePatient(@P("userId") @PathVariable Long userId, @AuthenticationPrincipal UserPrincipal principal) {
        service.deletePatient(userId);
    }
}
