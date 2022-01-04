package eu.codeacademy.projecttooth.tooth.controller;

import eu.codeacademy.projecttooth.tooth.model.Doctor;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;
import eu.codeacademy.projecttooth.tooth.service.DoctorEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Secured({"ROLE_DOCTOR", "ROLE_ADMIN"})
@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorEntityService doctorEntityService;

    @GetMapping("/account")
    public Doctor getDoctor(@AuthenticationPrincipal UserPrincipal principal) {
        return doctorEntityService.getDoctor(principal.getUserId());
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/register")
    public void createDoctor(@RequestBody Doctor doctor) {
        doctorEntityService.createDoctor(doctor);
    }

    @PutMapping
    public void updateDoctor(@RequestBody Doctor doctor) {
        doctorEntityService.updateDoctor(doctor);
    }

    @DeleteMapping
    public void deleteDoctor(@RequestBody Doctor doctor) {
        doctorEntityService.deleteDoctor(doctor);
    }
}
