package eu.codeacademy.projecttooth.tooth.service;

import eu.codeacademy.projecttooth.tooth.dto.ModifyDoctorServiceAvailabilityDto;
import eu.codeacademy.projecttooth.tooth.model.DoctorServiceAvailability;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface DoctorServiceAvailabilityService {

    DoctorServiceAvailability createAvailabilityService(ModifyDoctorServiceAvailabilityDto doctorServiceAvailability, Long userId);

    Page<DoctorServiceAvailability> getAvailabilityServiceAsPage(UserPrincipal principal, int pageNumber, int pageSize);

    DoctorServiceAvailability updateAvailabilityService(ModifyDoctorServiceAvailabilityDto doctorServiceAvailability, Long userId);

    Long deleteAvailabilityService(Long serviceId, UserPrincipal principal);

    DoctorServiceAvailability getAvailabilityService(Long userId, Long availabilityServiceId);

//    Page<DoctorServiceAvailability> getAvailabilityServicePageableAsPatient(int pageNumber, int pageSize);

    void deleteExpiredServiceAvailability();
}
