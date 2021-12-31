package eu.codeacademy.projecttooth.tooth.service;

import eu.codeacademy.projecttooth.tooth.model.Doctor;
import eu.codeacademy.projecttooth.tooth.model.DoctorAvailabilityService;
import eu.codeacademy.projecttooth.tooth.model.DoctorScheduler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DoctorAvailabilityServiceEntityService {
    void createService(List<DoctorAvailabilityService> doctorAvailabilityServiceList);


    List<DoctorScheduler> getAll(Doctor doctor);
}
