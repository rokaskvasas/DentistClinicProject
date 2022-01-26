package eu.codeacademy.projecttooth.tooth.service;


import eu.codeacademy.projecttooth.tooth.model.Doctor;
import org.springframework.data.domain.Page;

import java.util.List;


public interface DoctorService {
    Doctor createDoctor(Doctor doctor);

    Doctor getDoctorByUserId(Long userId);

    Doctor updateDoctor(Doctor doctor, Long userId);

    void deleteDoctor(Long userId);

    Page<Doctor> findAllDoctorsByStatus(String status, int pageNumber, int pageSize);

    Doctor verifyDoctor(Long doctorId);
}
