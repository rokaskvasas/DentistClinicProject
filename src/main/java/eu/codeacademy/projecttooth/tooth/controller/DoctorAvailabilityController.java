package eu.codeacademy.projecttooth.tooth.controller;

import eu.codeacademy.projecttooth.tooth.model.DoctorAvailability;
import eu.codeacademy.projecttooth.tooth.service.DoctorAvailabilityEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctorAvailability")
@RequiredArgsConstructor
public class DoctorAvailabilityController {

    private final DoctorAvailabilityEntityService service;

    @GetMapping("/{doctorId}")
    public List<DoctorAvailability> getAllDoctorAvailabilities(@PathVariable Long doctorId){
        return service.getAvailabilityList(doctorId);
    }

    @PostMapping
    public void createDoctorAvailabilities(@RequestBody List<DoctorAvailability> doctorAvailabilityList){
        service.createAvailability(doctorAvailabilityList);
    }

    @PutMapping
    public void updateDoctorAvailability(@RequestBody DoctorAvailability doctorAvailability){
        service.updateAvailability(doctorAvailability);
    }
}
