package eu.codeacademy.projecttooth.tooth.controller;

import eu.codeacademy.projecttooth.tooth.dto.ModifyAppointmentDto;
import eu.codeacademy.projecttooth.tooth.model.Appointment;
import eu.codeacademy.projecttooth.tooth.model.AppointmentSearchCriteria;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;
import eu.codeacademy.projecttooth.tooth.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@Secured({"ROLE_PATIENT", "ROLE_DOCTOR", "ROLE_ADMIN"})
@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;


    @GetMapping
    public Page<Appointment> getAllAppointmentsAsPatient(@AuthenticationPrincipal UserPrincipal principal,
                                                         AppointmentSearchCriteria searchCriteria,
                                                         @RequestParam(name = "page", required = false, defaultValue = "0") int pageNumber,
                                                         @RequestParam(name = "pageSize", required = false, defaultValue = "5") int pageSize) {
        return appointmentService.findAllAppointments(principal, searchCriteria, pageNumber, pageSize);
    }

    @GetMapping("/{appointmentId}/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') || #userId == principal.getUserId()")
    public Appointment getAppointmentAsPatient(@PathVariable Long appointmentId, @P("userId") @PathVariable Long userId, @AuthenticationPrincipal UserPrincipal principal) {
        return appointmentService.getAppointmentAsPatient(appointmentId, userId);
    }

    @GetMapping("/doctors/{appointmentId}/{userId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') || #userId == principal.getUserId()")
    public Appointment getAppointmentAsDoctor(@PathVariable Long appointmentId, @P("userId") @PathVariable Long userId, @AuthenticationPrincipal UserPrincipal principal) {
        return appointmentService.getAppointmentAsDoctor(appointmentId, userId);
    }


    @GetMapping("/doctors")
    public Page<Appointment> getAllAppointmentsAsDoctor(@AuthenticationPrincipal UserPrincipal principal,
                                                        AppointmentSearchCriteria searchCriteria,
                                                        @RequestParam(name = "page", required = false, defaultValue = "0") int pageNumber,
                                                        @RequestParam(name = "pageSize", required = false, defaultValue = "5") int pageSize) {
        return appointmentService.findAllAppointments(principal, searchCriteria, pageNumber, pageSize);
    }


    @PostMapping
    public Appointment createAppointment(@AuthenticationPrincipal UserPrincipal principal, @RequestBody ModifyAppointmentDto payload) {
        return appointmentService.createAppointment(principal.getUserId(), payload);
    }

    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @PutMapping
    public Appointment updateAppointment(@AuthenticationPrincipal UserPrincipal principal, @RequestBody ModifyAppointmentDto payload) {
        return appointmentService.updateAppointment(principal.getUserId(), payload);
    }

    @DeleteMapping("{id}")
    public void deleteAppointment(@AuthenticationPrincipal UserPrincipal principal, @PathVariable(name = "id") Long appointmentId) {
        appointmentService.deleteAppointment(principal.getUserId(), appointmentId);
    }


}
