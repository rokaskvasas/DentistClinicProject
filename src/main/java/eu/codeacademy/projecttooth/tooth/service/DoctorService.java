package eu.codeacademy.projecttooth.tooth.service;


import eu.codeacademy.projecttooth.tooth.dto.DoctorDto;
import eu.codeacademy.projecttooth.tooth.dto.DoctorRegisterDto;
import eu.codeacademy.projecttooth.tooth.entity.DoctorEntity;
import eu.codeacademy.projecttooth.tooth.model.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;


public interface DoctorService {

    DoctorRegisterDto createDoctor(Doctor doctor);

    DoctorRegisterDto getDoctorByUserId(Long userId);

    DoctorRegisterDto updateDoctor(DoctorDto doctorDto, Long userId);

    Long deleteDoctor(Long userId);

    Page<Doctor> findAllDoctorsByStatus(String status, int pageNumber, int pageSize);

    DoctorRegisterDto verifyDoctor(Long doctorId);

    List<DoctorEntity> findAllDoctorsWithSpecification(Specification<DoctorEntity> specification);

    void deleteUnverifiedDoctors(Iterable<DoctorEntity> expiredDoctors);

    DoctorEntity findDoctorEntity(Long userId);
}
