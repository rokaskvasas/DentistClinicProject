package eu.codeacademy.projecttooth.tooth.controller;

import eu.codeacademy.projecttooth.tooth.model.DoctorScheduler;
import eu.codeacademy.projecttooth.tooth.service.DoctorServiceAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/doctorScheduler")
@RequiredArgsConstructor
public class DoctorSchedulerController {

  private final DoctorServiceAvailabilityService service;

    @GetMapping
    public List<DoctorScheduler> getAllDoctorAvailabilityServicesList(){
        return  null;
    }
}
