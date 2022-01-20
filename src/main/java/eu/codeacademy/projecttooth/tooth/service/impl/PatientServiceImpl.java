package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.entity.PatientEntity;
import eu.codeacademy.projecttooth.tooth.exception.ObjectNotFoundException;
import eu.codeacademy.projecttooth.tooth.mapper.PatientMapper;
import eu.codeacademy.projecttooth.tooth.mapper.UserMapper;
import eu.codeacademy.projecttooth.tooth.model.Patient;
import eu.codeacademy.projecttooth.tooth.model.modelenum.RoleEnum;
import eu.codeacademy.projecttooth.tooth.repository.PatientRepository;
import eu.codeacademy.projecttooth.tooth.security.PasswordService;
import eu.codeacademy.projecttooth.tooth.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    private final PatientMapper patientMapper;

    private final UserMapper userMapper;

    private final PasswordService passwordService;

    @Override
    public Patient createPatient(Patient patient) {
        patient.setPassword(passwordService.encode(patient.getPassword()));
        patient.setRole(RoleEnum.ROLE_PATIENT.determinateRole());
        return patientMapper.createModel(patientRepository.saveAndFlush(patientMapper.createEntity(userMapper.getUserEntity(patient))));

    }

    @Override
    public Patient getPatient(Long userId) {
        return patientRepository.findByUserUserId(userId).map(patientMapper::createModel)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Patient with id:%s not found", userId)));
    }

    @Override
    public Patient updatePatient(Patient patient, Long userId) {
        return patientMapper.createModel(patientRepository.saveAndFlush(updatePatientEntity(patient, userId)));

    }

    @Override
    public void deletePatient(Long userId) {
        patientRepository.delete(getPatientEntity(userId));
    }


    private PatientEntity getPatientEntity(Long userId) {
        return patientRepository.findByUserUserId(userId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("PatientEntity with id:%s not found", userId)));
    }

    private PatientEntity updatePatientEntity(Patient patient, Long userId) {
        PatientEntity entity = getPatientEntity(userId);
        entity.getUser().setFirstName(patient.getFirstName());
        entity.getUser().setLastName(patient.getLastName());
        entity.getUser().setPhoneNumber(patient.getPhoneNumber());
        return entity;
    }

}
