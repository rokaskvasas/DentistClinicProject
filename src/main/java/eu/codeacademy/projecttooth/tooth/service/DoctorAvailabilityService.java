package eu.codeacademy.projecttooth.tooth.service;

import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.model.DoctorAvailability;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DoctorAvailabilityService {
    List<DoctorAvailability> createAvailability(List<DoctorAvailability> doctorAvailabilities, Long userId);

    DoctorAvailability updateAvailability(DoctorAvailability doctorAvailability, Long userId);

    void deleteAvailability(Long doctorAvailabilityId,UserPrincipal principal);

    DoctorAvailability getAvailability(Long availabilityId, UserPrincipal principal);

    Page<DoctorAvailability> getAvailabilityPageable(UserPrincipal principal, int pageNumber, int pageSize);


    void deleteExpiredAvailabilities();
}
