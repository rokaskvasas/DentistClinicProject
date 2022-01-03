package eu.codeacademy.projecttooth.tooth.controller;

import eu.codeacademy.projecttooth.tooth.model.DoctorAvailabilityService;
import eu.codeacademy.projecttooth.tooth.service.DoctorAvailabilityServiceEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Secured({"ROLE_DOCTOR", "ROLE_ADMIN"})
@RestController
@RequestMapping("/doctorAvailabilityService")
@RequiredArgsConstructor
public class DoctorAvailabilityServiceController {

    private final DoctorAvailabilityServiceEntityService doctorAvailabilityServiceEntityService;


    @PostMapping
    public void createDoctorAvailabilityService(@RequestBody List<DoctorAvailabilityService> doctorAvailabilityServiceList){
        doctorAvailabilityServiceEntityService.createService(doctorAvailabilityServiceList);

    }
}
