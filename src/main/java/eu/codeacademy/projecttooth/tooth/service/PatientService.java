package eu.codeacademy.projecttooth.tooth.service;

import eu.codeacademy.projecttooth.tooth.dto.PatientDto;
import eu.codeacademy.projecttooth.tooth.dto.PatientRegisterDto;
import eu.codeacademy.projecttooth.tooth.entity.PatientEntity;
import eu.codeacademy.projecttooth.tooth.model.Patient;
import org.springframework.stereotype.Service;

@Service
public interface PatientService {
    PatientRegisterDto createPatient(Patient patient);

    Patient getPatient(Long userId);

    Patient updatePatient(PatientDto patientDto, Long userId);

    PatientEntity getPatientEntity(Long userId);

    void deletePatient(Long userId);
}
