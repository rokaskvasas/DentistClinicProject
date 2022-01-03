package eu.codeacademy.projecttooth.tooth.controller;

import eu.codeacademy.projecttooth.tooth.model.DoctorAvailabilityService;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;
import eu.codeacademy.projecttooth.tooth.service.DoctorAvailabilityServiceEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Secured({"ROLE_DOCTOR", "ROLE_ADMIN"})
@RestController
@RequestMapping("/doctorAvailabilityService")
@RequiredArgsConstructor
public class DoctorAvailabilityServiceController {

    private final DoctorAvailabilityServiceEntityService service;


    @GetMapping
    public List<DoctorAvailabilityService> getAllDoctorAvailabilityServiceList(@AuthenticationPrincipal UserPrincipal principal) {
        return service.getAvailabilityServiceList(principal);
    }

    @PostMapping
    public void createDoctorAvailabilityService(@RequestBody List<DoctorAvailabilityService> doctorAvailabilityServiceList) {
        service.createAvailabilityService(doctorAvailabilityServiceList);
    }

    @PutMapping
    public void updateDoctorAvailabilityService(@RequestBody DoctorAvailabilityService doctorAvailabilityService){
        service.updateAvailabilityService(doctorAvailabilityService);
    }
}
