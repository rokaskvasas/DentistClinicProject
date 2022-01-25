package eu.codeacademy.projecttooth.tooth.service;

import eu.codeacademy.projecttooth.tooth.entity.PatientEntity;
import eu.codeacademy.projecttooth.tooth.model.Patient;
import org.springframework.stereotype.Service;

@Service
public interface PatientService {
    Patient createPatient(Patient patient);

    Patient getPatient(Long userId);

    Patient updatePatient(Patient patient, Long userId);

    PatientEntity getPatientEntity(Long userId);

    void deletePatient(Long userId);
}
