package eu.codeacademy.projecttooth.tooth.service;


import eu.codeacademy.projecttooth.tooth.model.Doctor;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DoctorService {
    Doctor createDoctor(Doctor doctor);


    Doctor getDoctor(Long doctorId);

    void updateDoctor(Doctor doctor, Long userId);


    void deleteDoctor(Long userId);

    List<Doctor> getDoctorList(String approved);

    void verifyDoctor(Long doctorId);
}
