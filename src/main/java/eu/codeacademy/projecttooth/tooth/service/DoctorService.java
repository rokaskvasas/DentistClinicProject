package eu.codeacademy.projecttooth.tooth.service;

import eu.codeacademy.projecttooth.tooth.model.Doctor;
import org.springframework.stereotype.Service;

@Service
public interface DoctorService {
    void createDoctor(Doctor doctor);


    Doctor getDoctor(Long doctorId);

    void updateDoctor(Doctor doctor);


    void deleteDoctor(Doctor doctor);
}
