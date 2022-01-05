package eu.codeacademy.projecttooth.tooth.service;

import eu.codeacademy.projecttooth.tooth.model.DoctorServiceAvailability;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DoctorServiceAvailabilityService {
    void createAvailabilityService(DoctorServiceAvailability doctorServiceAvailability, Long userId);


    List<DoctorServiceAvailability> getAvailabilityServiceList(Long userId);

    void updateAvailabilityService(DoctorServiceAvailability doctorServiceAvailability, Long userId);

    void deleteAvailabilityService(Long serviceId, Long userId);

    DoctorServiceAvailability getAvailabilityService(Long userId, Long availabilityServiceId);
}
