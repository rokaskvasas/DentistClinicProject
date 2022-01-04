package eu.codeacademy.projecttooth.tooth.service;

import eu.codeacademy.projecttooth.tooth.model.Patient;
import eu.codeacademy.projecttooth.tooth.model.User;
import org.springframework.stereotype.Service;

@Service
public interface PatientService {
    void createPatient(Patient patient);

    Patient getPatient(Long userId);

    void updatePatient(Patient patient);
}
