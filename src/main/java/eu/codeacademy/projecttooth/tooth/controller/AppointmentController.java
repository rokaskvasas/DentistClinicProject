package eu.codeacademy.projecttooth.tooth.controller;

import eu.codeacademy.projecttooth.tooth.dto.AppointmentDto;
import eu.codeacademy.projecttooth.tooth.dto.DoctorServiceAvailabilityDto;
import eu.codeacademy.projecttooth.tooth.model.Appointment;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;
import eu.codeacademy.projecttooth.tooth.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Secured({"ROLE_PATIENT", "ROLE_DOCTOR", "ROLE_ADMIN"})
@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService service;

    @GetMapping
    public List<AppointmentDto> getAllAppointments(@AuthenticationPrincipal UserPrincipal principal) {
        return service.getAppointmentList(principal.getUserId());
    }

    @GetMapping("/{id}")
    public AppointmentDto getAppointment(@AuthenticationPrincipal UserPrincipal principal, @PathVariable(name = "id") Long appointmentId) {
        return service.getAppointment(principal.getUserId(), appointmentId);
    }

    @PostMapping
    public void createAppointment(@AuthenticationPrincipal UserPrincipal principal, @RequestBody DoctorServiceAvailabilityDto payload) {
        service.createAppointment(principal.getUserId(), payload);
    }

}
