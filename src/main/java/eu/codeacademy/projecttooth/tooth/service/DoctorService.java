package eu.codeacademy.projecttooth.tooth.service;


import eu.codeacademy.projecttooth.tooth.model.Doctor;
import org.springframework.data.domain.Page;

import java.util.List;


public interface DoctorService {
    Doctor createDoctor(Doctor doctor);

    Doctor getDoctor(Long doctorId);

    Doctor updateDoctor(Doctor doctor, Long userId);

    void deleteDoctor(Long userId);

    List<Doctor> getUnverifiedDoctors(String approved, int pageNumber, int pageSize);

    Doctor verifyDoctor(Long doctorId);
}
