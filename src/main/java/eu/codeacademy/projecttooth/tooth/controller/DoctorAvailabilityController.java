package eu.codeacademy.projecttooth.tooth.controller;

import eu.codeacademy.projecttooth.tooth.model.DoctorAvailability;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;
import eu.codeacademy.projecttooth.tooth.service.DoctorAvailabilityEntityService;
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

    private final DoctorAvailabilityEntityService service;

    @GetMapping()
    public List<DoctorAvailability> getAllDoctorAvailabilityList(@AuthenticationPrincipal UserPrincipal principal){
        return service.getAvailabilityList(principal);
    }

    @PostMapping
    public void createDoctorAvailabilities(@RequestBody List<DoctorAvailability> doctorAvailabilityList) {
        service.createAvailability(doctorAvailabilityList);
    }

    @PutMapping
    public void updateDoctorAvailability(@RequestBody DoctorAvailability doctorAvailability) {
        service.updateAvailability(doctorAvailability);
    }

    @DeleteMapping
    public void deleteDoctorAvailability(@RequestBody DoctorAvailability doctorAvailability){
        service.deleteAvailability(doctorAvailability);
    }
}
