package eu.codeacademy.projecttooth.tooth.service;

import eu.codeacademy.projecttooth.tooth.model.Patient;
import org.springframework.stereotype.Service;

@Service
public interface PatientEntityService {
    void createPatient(Patient patient);
}
