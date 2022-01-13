package eu.codeacademy.projecttooth.tooth.controller;

import eu.codeacademy.projecttooth.tooth.model.Doctor;
import eu.codeacademy.projecttooth.tooth.model.User;
import eu.codeacademy.projecttooth.tooth.model.modelenum.StatusEnum;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;
import eu.codeacademy.projecttooth.tooth.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Secured({"ROLE_DOCTOR", "ROLE_ADMIN"})
@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService service;

    @PreAuthorize("hasAnyRole('ROLE_DOCTOR','ROLE_UNVERIFIED_DOCTOR' )")
    @GetMapping("/account")
    public Doctor getDoctor(@AuthenticationPrincipal UserPrincipal principal) {
        return service.getDoctor(principal.getUserId());
    }


    //    return object????
    @PreAuthorize("permitAll()")
    @PostMapping
    public void createDoctor(@RequestBody Doctor doctor) {
        service.createDoctor(doctor);
    }


    @PutMapping
    public void updateDoctor(@AuthenticationPrincipal UserPrincipal principal, @RequestBody Doctor doctor) {
        service.updateDoctor(doctor, principal.getUserId());
    }

    //    click delete ir auto log out
    @DeleteMapping
    public void deleteDoctor(@AuthenticationPrincipal UserPrincipal principal) {
        service.deleteDoctor(principal.getUserId());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/list")
    public List<Doctor> getUnverifiedDoctorList(@RequestParam(name = "approved", required = false, defaultValue = "UNVERIFIED") String approved) {
        return service.getDoctorList(approved);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/admin/{id}")
    public void verifyDoctor(@PathVariable(name = "id") Long doctorId) {
        service.verifyDoctor(doctorId);
    }
}
