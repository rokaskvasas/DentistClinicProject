package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.entity.UserEntity;
import eu.codeacademy.projecttooth.tooth.mapper.UserEntityMapper;
import eu.codeacademy.projecttooth.tooth.model.Patient;
import eu.codeacademy.projecttooth.tooth.model.modelenum.RoleEnum;
import eu.codeacademy.projecttooth.tooth.repository.PatientEntityRepository;
import eu.codeacademy.projecttooth.tooth.repository.UserEntityRepository;
import eu.codeacademy.projecttooth.tooth.service.PatientEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientEntityServiceImpl implements PatientEntityService {

    private final PatientEntityRepository patientEntityRepository;

    private final UserEntityRepository userEntityRepository;

    private final UserEntityMapper userEntityMapper;

    @Override
    public void signupPatient(Patient patient) {
        userEntityRepository.saveAndFlush(userEntityMapper.getUserEntity(patient, RoleEnum.PATIENT));

    }
}
