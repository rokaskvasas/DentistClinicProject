package eu.codeacademy.projecttooth.tooth.service;


import eu.codeacademy.projecttooth.tooth.dto.DoctorDto;
import eu.codeacademy.projecttooth.tooth.model.Doctor;
import org.springframework.data.domain.Page;


public interface DoctorService {
    Doctor createDoctor(Doctor doctor);

    Doctor getDoctorByUserId(Long userId);

    Doctor updateDoctor(DoctorDto doctorDto, Long userId);

    Long deleteDoctor(Long userId);

    Page<Doctor> findAllDoctorsByStatus(String status, int pageNumber, int pageSize);

    Doctor verifyDoctor(Long doctorId);
}
