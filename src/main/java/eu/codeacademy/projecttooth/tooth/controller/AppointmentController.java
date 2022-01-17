package eu.codeacademy.projecttooth.tooth.controller;

import eu.codeacademy.projecttooth.tooth.dto.ModifyAppointmentDto;
import eu.codeacademy.projecttooth.tooth.model.Appointment;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;
import eu.codeacademy.projecttooth.tooth.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Secured({"ROLE_PATIENT", "ROLE_DOCTOR", "ROLE_ADMIN"})
@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService service;

    @GetMapping
    public Page<Appointment> getAllAppointments(@AuthenticationPrincipal UserPrincipal principal,
                                                @RequestParam(name = "page", required = false, defaultValue = "0") int pageNumber,
                                                @RequestParam(name = "pageSize", required = false, defaultValue = "5") int pageSize) {
        return service.getAppointmentPageable(principal.getUserId(), pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    public Appointment getAppointment(@AuthenticationPrincipal UserPrincipal principal, @PathVariable(name = "id") Long appointmentId) {
        return service.getAppointment(principal.getUserId(), appointmentId);
    }

    @PostMapping
    public Appointment createAppointment(@AuthenticationPrincipal UserPrincipal principal, @RequestBody ModifyAppointmentDto payload) {
        return service.createAppointment(principal.getUserId(), payload);
    }

    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @PutMapping
    public Appointment updateAppointment(@AuthenticationPrincipal UserPrincipal principal, @RequestBody ModifyAppointmentDto payload) {
        return service.updateAppointment(principal.getUserId(), payload);
    }


    @DeleteMapping("{id}")
    public void deleteAppointment(@AuthenticationPrincipal UserPrincipal principal, @PathVariable(name = "id") Long appointmentId) {
        service.deleteAppointment(principal.getUserId(), appointmentId);
    }

}
