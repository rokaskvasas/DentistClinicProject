package eu.codeacademy.projecttooth.tooth.service;

import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.model.DoctorAvailability;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DoctorAvailabilityEntityService {
    void createAvailability(List<DoctorAvailability> doctorAvailabilities);

    List<DoctorAvailability> getAvailabilityList();
}
