package eu.codeacademy.projecttooth.tooth.controller;

import eu.codeacademy.projecttooth.tooth.dto.DoctorDto;
import eu.codeacademy.projecttooth.tooth.dto.DoctorResponseDto;
import eu.codeacademy.projecttooth.tooth.model.Doctor;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;
import eu.codeacademy.projecttooth.tooth.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Secured({"ROLE_DOCTOR", "ROLE_ADMIN"})
@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PreAuthorize("hasAnyRole('ROLE_DOCTOR','ROLE_UNVERIFIED_DOCTOR' )")
    @GetMapping("/account")
    public DoctorResponseDto getDoctor(@AuthenticationPrincipal UserPrincipal principal) {
        return doctorService.getDoctorByUserId(principal.getUserId());
    }


    @PreAuthorize("permitAll()")
    @PostMapping
    public DoctorResponseDto createDoctor(@RequestBody @Validated Doctor doctor) {
        return doctorService.createDoctor(doctor);
    }


    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') || #userId == principal.getUserId()")
    public DoctorResponseDto updateDoctor(@P("userId") @PathVariable Long userId, @AuthenticationPrincipal UserPrincipal principal, @Validated @RequestBody DoctorDto doctorDto) {
        return doctorService.updateDoctor(doctorDto, userId);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') || #userId == principal.getUserId()")
    public Long deleteDoctor(@P("userId") @PathVariable Long userId, @AuthenticationPrincipal UserPrincipal principal) {
        return doctorService.deleteDoctor(userId);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/list")
    public Page<Doctor> findAllDoctorsByStatus(@RequestParam(name = "status", required = false, defaultValue = "UNVERIFIED") String status,
                                               @RequestParam(name = "page", required = false, defaultValue = "0") int pageNumber,
                                               @RequestParam(name = "pageSize", required = false, defaultValue = "5") int pageSize) {
        return doctorService.findAllDoctorsByStatus(status, pageNumber, pageSize);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/admin/{id}")
    public DoctorResponseDto verifyDoctor(@PathVariable(name = "id") Long doctorId) {
        return doctorService.verifyDoctor(doctorId);
    }
}
