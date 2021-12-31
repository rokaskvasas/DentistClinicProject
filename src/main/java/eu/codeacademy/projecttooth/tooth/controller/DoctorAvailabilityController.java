package eu.codeacademy.projecttooth.tooth.controller;

import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.model.DoctorAvailability;
import eu.codeacademy.projecttooth.tooth.service.DoctorAvailabilityEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctorAvailability")
@RequiredArgsConstructor
public class DoctorAvailabilityController {

    private final DoctorAvailabilityEntityService doctorAvailabilityEntityService;

    @GetMapping
    public List<DoctorAvailability> getAllDoctorAvailabilities(){
        return doctorAvailabilityEntityService.getAvailabilityList();
    }

    @PostMapping
    public void createDoctorAvailabilities(@RequestBody List<DoctorAvailability> doctorAvailabilityList){
        doctorAvailabilityEntityService.createAvailability(doctorAvailabilityList);
    }
}
