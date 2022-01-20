package eu.codeacademy.projecttooth.tooth.controller;

import eu.codeacademy.projecttooth.tooth.dto.DoctorRegisterDto;
import eu.codeacademy.projecttooth.tooth.model.Doctor;
import eu.codeacademy.projecttooth.tooth.model.Location;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;
import eu.codeacademy.projecttooth.tooth.service.DoctorService;
import eu.codeacademy.projecttooth.tooth.service.LocationService;
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

    private final DoctorService doctorService;

    @PreAuthorize("hasAnyRole('ROLE_DOCTOR','ROLE_UNVERIFIED_DOCTOR' )")
    @GetMapping("/account")
    public Doctor getDoctor(@AuthenticationPrincipal UserPrincipal principal) {
        return doctorService.getDoctor(principal.getUserId());
    }


    @PreAuthorize("permitAll()")
    @PostMapping
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        return doctorService.createDoctor(doctor);
    }


    @PutMapping
    public Doctor updateDoctor(@AuthenticationPrincipal UserPrincipal principal, @RequestBody Doctor doctor) {
        return doctorService.updateDoctor(doctor, principal.getUserId());
    }

    @DeleteMapping
    public void deleteDoctor(@AuthenticationPrincipal UserPrincipal principal) {
        doctorService.deleteDoctor(principal.getUserId());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/list")
    public List<Doctor> getUnverifiedDoctors(@RequestParam(name = "approved", required = false, defaultValue = "UNVERIFIED") String approved,
                                             @RequestParam(name = "page", required = false, defaultValue = "0") int pageNumber,
                                             @RequestParam(name = "pageSize", required = false, defaultValue = "5") int pageSize) {
        return doctorService.getUnverifiedDoctors(approved, pageNumber, pageSize);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/admin/{id}")
    public Doctor verifyDoctor(@PathVariable(name = "id") Long doctorId) {
        return doctorService.verifyDoctor(doctorId);
    }
}
