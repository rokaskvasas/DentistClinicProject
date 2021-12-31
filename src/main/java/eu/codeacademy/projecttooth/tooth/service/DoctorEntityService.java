package eu.codeacademy.projecttooth.tooth.service;

import eu.codeacademy.projecttooth.tooth.model.Doctor;
import org.springframework.stereotype.Service;

@Service
public interface DoctorEntityService {
    void createDoctor(Doctor doctor);


    Doctor getDoctor(Doctor doctor);
}
