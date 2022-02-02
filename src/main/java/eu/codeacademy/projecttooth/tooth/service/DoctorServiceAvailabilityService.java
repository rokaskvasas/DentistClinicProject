package eu.codeacademy.projecttooth.tooth.service;

import eu.codeacademy.projecttooth.tooth.dto.DoctorServiceAvailabilityResponse;
import eu.codeacademy.projecttooth.tooth.dto.ModifyDoctorServiceAvailabilityDto;
import eu.codeacademy.projecttooth.tooth.entity.DoctorServiceAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.model.DoctorServiceAvailabilitySearchCriteria;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface DoctorServiceAvailabilityService {

    DoctorServiceAvailabilityResponse createAvailabilityService(ModifyDoctorServiceAvailabilityDto doctorServiceAvailability, Long userId);

    Page<DoctorServiceAvailabilityResponse> getAvailabilityServiceAsPage(UserPrincipal principal, int pageNumber, int pageSize);

    DoctorServiceAvailabilityResponse updateAvailabilityService(ModifyDoctorServiceAvailabilityDto doctorServiceAvailability, Long userId);

    Long deleteAvailabilityService(Long serviceId, UserPrincipal principal);

    DoctorServiceAvailabilityResponse getAvailabilityService(UserPrincipal principal, Long availabilityServiceId);

    void deleteExpiredServiceAvailability();

    void updateDoctorServiceAvailabilityToReservedAndSaveToDatabase(DoctorServiceAvailabilityEntity doctorServiceAvailability);


    Page<DoctorServiceAvailabilityResponse> findAvailableDoctorServiceAvailablities(DoctorServiceAvailabilitySearchCriteria doctorServiceAvailabilitySearchCriteria, int pageNumber, int pageSize);

    DoctorServiceAvailabilityEntity findDoctorServiceAvailabilityEntityById(Long doctorServiceAvailabilityId);
}
