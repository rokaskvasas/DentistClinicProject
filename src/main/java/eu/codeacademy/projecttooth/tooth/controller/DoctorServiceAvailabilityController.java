package eu.codeacademy.projecttooth.tooth.controller;

import eu.codeacademy.projecttooth.tooth.dto.ModifyDoctorServiceAvailabilityDto;
import eu.codeacademy.projecttooth.tooth.model.DoctorServiceAvailability;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;
import eu.codeacademy.projecttooth.tooth.service.DoctorServiceAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Secured({"ROLE_DOCTOR", "ROLE_ADMIN"})
@RestController
@RequestMapping("/doctorAvailabilityService")
@RequiredArgsConstructor
public class DoctorServiceAvailabilityController {

    private final DoctorServiceAvailabilityService service;


    @PreAuthorize("hasAnyRole('ROLE_PATIENT','ROLE_ADMIN')")
    @GetMapping("/available")
    public Page<DoctorServiceAvailability> getAllDoctorAvailabilityServiceListAsPatientAndAdmin(@AuthenticationPrincipal UserPrincipal principal,
                                                                                                @RequestParam(name = "page", required = false, defaultValue = "0") int pageNumber,
                                                                                                @RequestParam(name = "pageSize", required = false, defaultValue = "5") int pageSize) {
        return service.getAvailabilityServiceAsPage(principal, pageNumber, pageSize);
    }

    @GetMapping
    public Page<DoctorServiceAvailability> getAllDoctorAvailabilityService(@AuthenticationPrincipal UserPrincipal principal,
                                                                           @RequestParam(name = "page", required = false, defaultValue = "0") int pageNumber,
                                                                           @RequestParam(name = "pageSize", required = false, defaultValue = "5") int pageSize) {
        return service.getAvailabilityServiceAsPage(principal, pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    public DoctorServiceAvailability getDoctorAvailabilityService(@AuthenticationPrincipal UserPrincipal principal,
                                                                  @PathVariable(name = "id") Long availabilityServiceId) {
        return service.getAvailabilityService(principal.getUserId(), availabilityServiceId);
    }

    @PostMapping
    public DoctorServiceAvailability createDoctorServiceAvailability(@AuthenticationPrincipal UserPrincipal principal,
                                                                     @RequestBody ModifyDoctorServiceAvailabilityDto payload) {
        return service.createAvailabilityService(payload, principal.getUserId());
    }

    @PutMapping
    public DoctorServiceAvailability updateDoctorAvailabilityService(@AuthenticationPrincipal UserPrincipal principal,
                                                                     @RequestBody ModifyDoctorServiceAvailabilityDto doctorServiceAvailability) {
        return service.updateAvailabilityService(doctorServiceAvailability, principal.getUserId());
    }

    @DeleteMapping("/{id}")
    public void deleteDoctorAvailabilityService(@AuthenticationPrincipal UserPrincipal principal, @PathVariable(name = "id") Long serviceId) {
        service.deleteAvailabilityService(serviceId, principal.getUserId());
    }
}
