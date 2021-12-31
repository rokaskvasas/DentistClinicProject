package eu.codeacademy.projecttooth.tooth.service;

import eu.codeacademy.projecttooth.tooth.model.DoctorAvailabilityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DoctorAvailabilityServiceEntityService {
    void createService(List<DoctorAvailabilityService> doctorAvailabilityServiceList);
}
