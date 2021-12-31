package eu.codeacademy.projecttooth.tooth.controller;

import eu.codeacademy.projecttooth.tooth.model.Doctor;
import eu.codeacademy.projecttooth.tooth.service.DoctorEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorEntityService doctorEntityService;

    @GetMapping("/account")
    public Doctor getDoctor(@RequestBody Doctor doctor){
        return doctorEntityService.getDoctor(doctor);
    }

    @PostMapping
    public void createDoctor(@RequestBody Doctor doctor) {
        doctorEntityService.createDoctor(doctor);
    }
}
