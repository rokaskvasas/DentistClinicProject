package eu.codeacademy.projecttooth.tooth.controller;

import eu.codeacademy.projecttooth.tooth.model.DoctorAvailabilityService;
import eu.codeacademy.projecttooth.tooth.service.DoctorAvailabilityServiceEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
