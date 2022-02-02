package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.dto.PatientDto;
import eu.codeacademy.projecttooth.tooth.dto.PatientRegisterDto;
import eu.codeacademy.projecttooth.tooth.entity.PatientEntity;
import eu.codeacademy.projecttooth.tooth.exception.ObjectNotFoundException;
import eu.codeacademy.projecttooth.tooth.mapper.PatientMapper;
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

    private final PasswordService passwordService;

    @Override
    public PatientRegisterDto createPatient(Patient patient) {
        PatientEntity patientEntity = createPatientEntity(patient);
        updateDatabase(patientEntity);
        return createPatientRegisterDtoModel(patientEntity);

    }

    private PatientRegisterDto createPatientRegisterDtoModel(PatientEntity entity) {
        return patientMapper.createPatientRegisterDtoModel(entity);
    }

    @Override
    public Patient getPatient(Long userId) {
        return patientRepository.findByUserUserId(userId).map(patientMapper::createModel)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Patient with id:%s not found", userId)));
    }

    @Override
    public Patient updatePatient(PatientDto patient, Long userId) {
        PatientEntity patientEntity = updatePatientEntity(patient, userId);
        updateDatabase(patientEntity);
        return createPatientModel(patientEntity);

    }

    @Override
    public void deletePatient(Long userId) {
        PatientEntity patientEntity = getPatientEntity(userId);
        patientRepository.delete(patientEntity);
    }

    @Override
    public PatientEntity getPatientEntity(Long userId) {
        return patientRepository.findByUserUserId(userId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Patient Entity with id:%s not found", userId)));
    }

    private PatientEntity updatePatientEntity(PatientDto patientDto, Long userId) {
        PatientEntity entity = getPatientEntity(userId);
        entity.getUser().setFirstName(patientDto.getFirstName());
        entity.getUser().setLastName(patientDto.getLastName());
        entity.getUser().setPhoneNumber(patientDto.getPhoneNumber());
        return entity;
    }

    private Patient createPatientModel(PatientEntity patientEntity) {
        return patientMapper.createModel(patientEntity);
    }

    private void updateDatabase(PatientEntity patientEntity) {
        patientRepository.saveAndFlush(patientEntity);
    }

    private PatientEntity createPatientEntity(Patient patient) {
        patient.setPassword(passwordService.encode(patient.getPassword()));
        patient.setRole(RoleEnum.ROLE_PATIENT.determinateRole());
        return patientMapper.createEntity(patient);
    }


}
