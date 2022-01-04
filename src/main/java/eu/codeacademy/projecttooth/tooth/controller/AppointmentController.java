package eu.codeacademy.projecttooth.tooth.controller;

import eu.codeacademy.projecttooth.tooth.model.DoctorAvailabilityService;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;
import eu.codeacademy.projecttooth.tooth.service.AppointmentEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Secured({"ROLE_PATIENT", "ROLE_DOCTOR", "ROLE_ADMIN"})
@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

//    private final AppointmentEntityService service;

//    @PostMapping
//    public void createAppointment(@AuthenticationPrincipal UserPrincipal principal, @RequestBody DoctorAvailabilityService doctorAvailabilityService) {
//        service.createAppointment(principal, doctorAvailabilityService);
//    }
}
