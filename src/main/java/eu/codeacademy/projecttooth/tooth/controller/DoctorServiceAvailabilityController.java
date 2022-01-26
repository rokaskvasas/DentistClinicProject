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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Secured({"ROLE_DOCTOR", "ROLE_ADMIN"})
@RestController
@RequestMapping("/doctorAvailabilityService")
@RequiredArgsConstructor
public class DoctorServiceAvailabilityController {

    private final DoctorServiceAvailabilityService doctorServiceAvailabilityService;


    @PreAuthorize("hasAnyRole('ROLE_PATIENT','ROLE_ADMIN')")
    @GetMapping("/available")
    public Page<DoctorServiceAvailability> getAllDoctorAvailabilityServiceListAsPatientAndAdmin(@AuthenticationPrincipal UserPrincipal principal,
                                                                                                @RequestParam(name = "page", required = false, defaultValue = "0") int pageNumber,
                                                                                                @RequestParam(name = "pageSize", required = false, defaultValue = "5") int pageSize,
                                                                                                @RequestParam(name = "location", required = false, defaultValue = "all") String location,
                                                                                                @RequestParam(name = "service", required = false, defaultValue = "all") String service) {
        return doctorServiceAvailabilityService.getAvailabilityServiceAsPatientAndAdmin(principal, pageNumber, pageSize, location, service);
    }


    @GetMapping
    public Page<DoctorServiceAvailability> getAllDoctorAvailabilityService(@AuthenticationPrincipal UserPrincipal principal,
                                                                           @RequestParam(name = "page", required = false, defaultValue = "0") int pageNumber,
                                                                           @RequestParam(name = "pageSize", required = false, defaultValue = "5") int pageSize) {
        return doctorServiceAvailabilityService.getAvailabilityServiceAsPage(principal, pageNumber, pageSize);
    }


    @GetMapping("/{id}")
    public DoctorServiceAvailability getDoctorAvailabilityService(@AuthenticationPrincipal UserPrincipal principal,
                                                                  @PathVariable(name = "id") Long availabilityServiceId) {
        return doctorServiceAvailabilityService.getAvailabilityService(principal.getUserId(), availabilityServiceId);
    }

    @PostMapping
    public DoctorServiceAvailability createDoctorServiceAvailability(@AuthenticationPrincipal UserPrincipal principal,
                                                                     @Validated @RequestBody ModifyDoctorServiceAvailabilityDto payload) {
        return doctorServiceAvailabilityService.createAvailabilityService(payload, principal.getUserId());
    }

    @PutMapping
    public DoctorServiceAvailability updateDoctorAvailabilityService(@AuthenticationPrincipal UserPrincipal principal,
                                                                     @RequestBody ModifyDoctorServiceAvailabilityDto doctorServiceAvailability) {
        return doctorServiceAvailabilityService.updateAvailabilityService(doctorServiceAvailability, principal.getUserId());
    }

    @DeleteMapping("/{id}")
    public Long deleteDoctorAvailabilityService(@AuthenticationPrincipal UserPrincipal principal, @PathVariable(name = "id") Long serviceId) {
        return doctorServiceAvailabilityService.deleteAvailabilityService(serviceId, principal);
    }
}
