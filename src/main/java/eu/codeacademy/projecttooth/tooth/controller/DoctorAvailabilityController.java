package eu.codeacademy.projecttooth.tooth.controller;

import eu.codeacademy.projecttooth.tooth.model.DoctorAvailability;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;
import eu.codeacademy.projecttooth.tooth.service.DoctorAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Secured({"ROLE_DOCTOR", "ROLE_ADMIN"})
@RestController
@RequestMapping("/doctorAvailability")
@RequiredArgsConstructor
public class DoctorAvailabilityController {

    private final DoctorAvailabilityService service;


    @GetMapping("/{id}")
    public DoctorAvailability getDoctorAvailability(@AuthenticationPrincipal UserPrincipal principal,
                                                    @PathVariable(name = "id") Long availabilityId) {
        return service.getAvailability(availabilityId, principal.getUserId());
    }

    @GetMapping()
    public List<DoctorAvailability> getAllDoctorAvailabilityList(@AuthenticationPrincipal UserPrincipal principal) {
        return service.getAvailabilityList(principal);
    }

    @PostMapping
    public void createDoctorAvailabilities(@AuthenticationPrincipal UserPrincipal principal,
                                           @RequestBody List<DoctorAvailability> doctorAvailabilityList) {
        service.createAvailability(doctorAvailabilityList, principal.getUserId());
    }

    @PutMapping
    public void updateDoctorAvailability(@AuthenticationPrincipal UserPrincipal principal,
                                         @RequestBody DoctorAvailability doctorAvailability) {
        service.updateAvailability(doctorAvailability, principal.getUserId());
    }

    @DeleteMapping("{id}")
    public void deleteDoctorAvailability(@AuthenticationPrincipal UserPrincipal principal,
                                         @PathVariable(name = "id") Long doctorAvailabilityId) {
        service.deleteAvailability(doctorAvailabilityId, principal.getUserId());
    }
}
