package eu.codeacademy.projecttooth.tooth.service;

import eu.codeacademy.projecttooth.tooth.dto.DoctorAvailabilityDto;
import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface DoctorAvailabilityService {

    DoctorAvailabilityDto createAvailability(DoctorAvailabilityDto doctorAvailability, Long userId);

    DoctorAvailabilityDto updateAvailability(DoctorAvailabilityDto doctorAvailability, Long userId);

    Long deleteAvailability(Long doctorAvailabilityId, UserPrincipal principal);

    DoctorAvailabilityDto getAvailability(Long availabilityId, UserPrincipal principal);

    Page<DoctorAvailabilityDto> getAvailabilityPageable(UserPrincipal principal, int pageNumber, int pageSize);


    void deleteExpiredAvailabilities();

    DoctorAvailabilityEntity findDoctorAvailabilityEntityByUserAndAvailabilityId(Long availabilityId, Long userId);

    DoctorAvailabilityEntity getDoctorAvailabilityEntity(Long doctorAvailabilityId, Long userId);
}
