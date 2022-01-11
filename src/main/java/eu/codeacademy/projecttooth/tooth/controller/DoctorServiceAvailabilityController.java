package eu.codeacademy.projecttooth.tooth.controller;

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


    @PreAuthorize("hasRole('ROLE_PATIENT')")
    @GetMapping("/available")
    public Page<DoctorServiceAvailabilityDto> getAllDoctorAvailabilityServiceListAsPatient(@RequestParam(name = "page", required = false, defaultValue = "0") int pageNumber,
                                                                                           @RequestParam(name = "pageSize", required = false, defaultValue = "0") int pageSize) {
        return service.getAvailabilityServicePageableAsPatient(pageNumber, pageSize);
    }

    @GetMapping
    public Page<DoctorServiceAvailability> getAllDoctorAvailabilityService(@AuthenticationPrincipal UserPrincipal principal,
                                                                           @RequestParam(name = "page", required = false, defaultValue = "0") int pageNumber,
                                                                           @RequestParam(name = "pageSize", required = false, defaultValue = "0") int pageSize) {
        return service.getAvailabilityServiceAsPage(principal.getUserId(), pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    public DoctorServiceAvailability getDoctorAvailabilityService(@AuthenticationPrincipal UserPrincipal principal,
                                                                  @PathVariable(name = "id") Long availabilityServiceId) {
        return service.getAvailabilityService(principal.getUserId(), availabilityServiceId);
    }

    @PostMapping
    public void createDoctorAvailabilityService(@AuthenticationPrincipal UserPrincipal principal,
                                                @RequestBody DoctorServiceAvailability doctorServiceAvailability) {
        service.createAvailabilityService(doctorServiceAvailability, principal.getUserId());
    }

    @PutMapping
    public void updateDoctorAvailabilityService(@AuthenticationPrincipal UserPrincipal principal,
                                                @RequestBody DoctorServiceAvailability doctorServiceAvailability) {
        service.updateAvailabilityService(doctorServiceAvailability, principal.getUserId());
    }

    //    @Pathvariable su serviceID
    @DeleteMapping("/{id}")
    public void deleteDoctorAvailabilityService(@AuthenticationPrincipal UserPrincipal principal, @PathVariable(name = "id") Long serviceId) {
        service.deleteAvailabilityService(serviceId, principal.getUserId());
    }
}
