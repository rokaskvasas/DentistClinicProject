package eu.codeacademy.projecttooth.tooth.service;

import eu.codeacademy.projecttooth.tooth.model.Doctor;
import eu.codeacademy.projecttooth.tooth.model.DoctorAvailabilityService;
import eu.codeacademy.projecttooth.tooth.model.DoctorScheduler;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DoctorAvailabilityServiceEntityService {
    void createAvailabilityService(List<DoctorAvailabilityService> doctorAvailabilityServiceList);


    List<DoctorAvailabilityService> getAvailabilityServiceList(UserPrincipal principal);

    void updateAvailabilityService(DoctorAvailabilityService doctorAvailabilityService);
}
