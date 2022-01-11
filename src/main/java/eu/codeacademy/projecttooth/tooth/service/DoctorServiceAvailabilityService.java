package eu.codeacademy.projecttooth.tooth.service;

import eu.codeacademy.projecttooth.tooth.model.DoctorServiceAvailability;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface DoctorServiceAvailabilityService {
    void createAvailabilityService(DoctorServiceAvailability doctorServiceAvailability, Long userId);


    Page<DoctorServiceAvailability> getAvailabilityServiceAsPage(Long userId, int pageNumber, int pageSize);

    void updateAvailabilityService(DoctorServiceAvailability doctorServiceAvailability, Long userId);

    void deleteAvailabilityService(Long serviceId, Long userId);

    DoctorServiceAvailability getAvailabilityService(Long userId, Long availabilityServiceId);

    Page<DoctorServiceAvailability> getAvailabilityServicePageableAsPatient(int pageNumber, int pageSize);
}
