package eu.codeacademy.projecttooth.tooth.service;

import eu.codeacademy.projecttooth.tooth.dto.DoctorServiceAvailabilityResponse;
import eu.codeacademy.projecttooth.tooth.dto.ModifyDoctorServiceAvailabilityDto;
import eu.codeacademy.projecttooth.tooth.entity.DoctorServiceAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.model.DoctorServiceAvailability;
import eu.codeacademy.projecttooth.tooth.model.modelenum.ServiceEnum;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;
import eu.codeacademy.projecttooth.tooth.test.DSAPage;
import eu.codeacademy.projecttooth.tooth.test.DSASearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public interface DoctorServiceAvailabilityService {

    DoctorServiceAvailabilityResponse createAvailabilityService(ModifyDoctorServiceAvailabilityDto doctorServiceAvailability, Long userId);

    Page<DoctorServiceAvailabilityResponse> getAvailabilityServiceAsPage(UserPrincipal principal, int pageNumber, int pageSize);

    DoctorServiceAvailability updateAvailabilityService(ModifyDoctorServiceAvailabilityDto doctorServiceAvailability, Long userId);

    Long deleteAvailabilityService(Long serviceId, UserPrincipal principal);

    DoctorServiceAvailabilityResponse getAvailabilityService(UserPrincipal principal, Long availabilityServiceId);

    DoctorServiceAvailabilityEntity findDoctorServiceAvailabilityEntity(Long userId, Long availabilityServiceId);

    void deleteExpiredServiceAvailability();

    void updateDoctorServiceAvailabilityToReservedAndSaveToDatabase(DoctorServiceAvailabilityEntity doctorServiceAvailability);

    Page<DoctorServiceAvailability> getAvailabilityServiceAsPatientAndAdmin(UserPrincipal principal, int pageNumber, int pageSize);

    Page<DoctorServiceAvailability> findAvailableDoctorServiceAvailablities(String city, ServiceEnum service, LocalDateTime startTime, LocalDateTime endTime, String firstName, String lastName, DSAPage page);

    Page<DoctorServiceAvailability> testWithSearch(DSAPage dsaPage, DSASearchCriteria dsaSearchCriteria);
}
