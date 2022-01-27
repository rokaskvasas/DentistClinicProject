package eu.codeacademy.projecttooth.tooth.controller;

import eu.codeacademy.projecttooth.tooth.dto.DoctorServiceAvailabilityResponse;
import eu.codeacademy.projecttooth.tooth.dto.ModifyDoctorServiceAvailabilityDto;
import eu.codeacademy.projecttooth.tooth.model.DoctorServiceAvailability;
import eu.codeacademy.projecttooth.tooth.model.modelenum.ServiceEnum;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;
import eu.codeacademy.projecttooth.tooth.service.DoctorServiceAvailabilityService;
import eu.codeacademy.projecttooth.tooth.test.DSAPage;
import eu.codeacademy.projecttooth.tooth.test.DSASearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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
                                                                                                @RequestParam(name = "pageSize", required = false, defaultValue = "5") int pageSize) {
        return doctorServiceAvailabilityService.getAvailabilityServiceAsPatientAndAdmin(principal, pageNumber, pageSize);
    }

    @GetMapping
    public Page<DoctorServiceAvailabilityResponse> getAllDoctorAvailabilityService(@AuthenticationPrincipal UserPrincipal principal,
                                                                                   @RequestParam(name = "page", required = false, defaultValue = "0") int pageNumber,
                                                                                   @RequestParam(name = "pageSize", required = false, defaultValue = "5") int pageSize) {
        return doctorServiceAvailabilityService.getAvailabilityServiceAsPage(principal, pageNumber, pageSize);
    }


    @GetMapping("/{id}")
    public DoctorServiceAvailabilityResponse getDoctorAvailabilityService(@AuthenticationPrincipal UserPrincipal principal,
                                                                          @PathVariable(name = "id") Long availabilityServiceId) {
        return doctorServiceAvailabilityService.getAvailabilityService(principal, availabilityServiceId);
    }

    @PostMapping
    public DoctorServiceAvailabilityResponse createDoctorServiceAvailability(@AuthenticationPrincipal UserPrincipal principal,
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

    // problema su start ir endTime
    @GetMapping("/test")
    public Page<DoctorServiceAvailability> testing(DSAPage dsaPage, DSASearchCriteria dsaSearchCriteria) {
        return doctorServiceAvailabilityService.testWithSearch(dsaPage, dsaSearchCriteria);
    }

    @GetMapping("/test2")
    public Page<DoctorServiceAvailability> testing1(@RequestParam(name = "service", required = false) ServiceEnum serviceEnum,
                                                    @RequestParam(name = "city", required = false) String city,
                                                    @RequestParam(name = "startTime", required = false)
                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
                                                    @RequestParam(name = "endTime", required = false)
                                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
                                                    @RequestParam(name = "firstName", required = false) String firstName,
                                                    @RequestParam(name = "lastName", required = false) String lastName,
                                                    DSAPage dsaPage) {

        return doctorServiceAvailabilityService.findAvailableDoctorServiceAvailablities(city, serviceEnum, startTime, endTime, firstName, lastName, dsaPage);
    }


}
