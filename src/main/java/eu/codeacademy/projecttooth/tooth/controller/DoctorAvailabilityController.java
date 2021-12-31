package eu.codeacademy.projecttooth.tooth.controller;

import eu.codeacademy.projecttooth.tooth.model.DoctorAvailability;
import eu.codeacademy.projecttooth.tooth.service.DoctorAvailabilityEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/doctorAvailability")
@RequiredArgsConstructor
public class DoctorAvailabilityController {

    private final DoctorAvailabilityEntityService doctorAvailabilityEntityService;

    @PostMapping
    public void createDoctorAvailabilities(@RequestBody List<DoctorAvailability> doctorAvailabilities){
        doctorAvailabilityEntityService.createAvailability(doctorAvailabilities);
    }
}
