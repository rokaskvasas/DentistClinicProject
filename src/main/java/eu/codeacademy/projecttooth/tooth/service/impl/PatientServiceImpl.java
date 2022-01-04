package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.entity.PatientEntity;
import eu.codeacademy.projecttooth.tooth.exception.ObjectNotFoundException;
import eu.codeacademy.projecttooth.tooth.mapper.PatientMapper;
import eu.codeacademy.projecttooth.tooth.mapper.UserMapper;
import eu.codeacademy.projecttooth.tooth.model.Patient;
import eu.codeacademy.projecttooth.tooth.model.modelenum.RoleEnum;
import eu.codeacademy.projecttooth.tooth.repository.PatientEntityRepository;
import eu.codeacademy.projecttooth.tooth.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientEntityRepository patientEntityRepository;

    private final PatientMapper patientMapper;

    private final UserMapper userMapper;

    @Override
    public void createPatient(Patient patient) {
        patientEntityRepository.saveAndFlush(patientMapper.getEntity(userMapper.getUserEntity(patient, RoleEnum.PATIENT)));

    }

    @Override
    public Patient getPatient(Long userId) {
        return patientEntityRepository.findByUserUserId(userId).map(patientMapper::getModel)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Patient with id:%s not found", userId)));
    }

    @Override
    public void updatePatient(Patient patient) {
        PatientEntity patientEntity = patientEntityRepository.findById(patient.getPatientId())
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Patient with id:%s not found", patient.getPatientId())));
        patientEntityRepository.saveAndFlush(patientMapper.updateEntity(patient,patientEntity));

    }
}
