package eu.codeacademy.projecttooth.tooth.controller;

import eu.codeacademy.projecttooth.tooth.dto.ModifyDoctorAvailabilityDto;
import eu.codeacademy.projecttooth.tooth.model.DoctorAvailability;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;
import eu.codeacademy.projecttooth.tooth.service.DoctorAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Secured({"ROLE_DOCTOR", "ROLE_ADMIN"})
@RestController
@RequestMapping("/doctorAvailability")
@RequiredArgsConstructor
public class DoctorAvailabilityController {

    private final DoctorAvailabilityService service;

    @GetMapping("/{id}")
    public DoctorAvailability getDoctorAvailability(@AuthenticationPrincipal UserPrincipal principal,
                                                    @PathVariable(name = "id") Long availabilityId) {
        return service.getAvailability(availabilityId, principal);
    }

    @GetMapping()
    public Page<DoctorAvailability> getAllDoctorAvailabilityPageable(@AuthenticationPrincipal UserPrincipal principal,
                                                                     @RequestParam(name = "page", required = false, defaultValue = "0") int pageNumber,
                                                                     @RequestParam(name = "pageSize", required = false, defaultValue = "5") int pageSize) {
        return service.getAvailabilityPageable(principal, pageNumber, pageSize);
    }

    @PostMapping
    public DoctorAvailability createDoctorAvailability(@AuthenticationPrincipal UserPrincipal principal,
                                                       @RequestBody ModifyDoctorAvailabilityDto doctorAvailabilityDto) {
        return service.createAvailability(doctorAvailabilityDto, principal.getUserId());
    }

    @PutMapping
    public DoctorAvailability updateDoctorAvailability(@AuthenticationPrincipal UserPrincipal principal,
                                                       @RequestBody ModifyDoctorAvailabilityDto doctorAvailabilityDto) {
        return service.updateAvailability(doctorAvailabilityDto, principal.getUserId());
    }

    @DeleteMapping("{id}")
    public Long deleteDoctorAvailability(@AuthenticationPrincipal UserPrincipal principal,
                                         @PathVariable(name = "id") Long doctorAvailabilityId) {
        return service.deleteAvailability(doctorAvailabilityId, principal);
    }

}
