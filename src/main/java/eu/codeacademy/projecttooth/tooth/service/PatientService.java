package eu.codeacademy.projecttooth.tooth.service;

import eu.codeacademy.projecttooth.tooth.dto.PatientRequestDto;
import eu.codeacademy.projecttooth.tooth.dto.PatientResponseDto;
import eu.codeacademy.projecttooth.tooth.entity.PatientEntity;
import eu.codeacademy.projecttooth.tooth.model.Patient;
import org.springframework.stereotype.Service;

@Service
public interface PatientService {
    PatientResponseDto createPatient(Patient patient);

    Patient getPatient(Long userId);

    Patient updatePatient(PatientRequestDto patientRequestDto, Long userId);

    PatientEntity getPatientEntity(Long userId);

    void deletePatient(Long userId);
}
