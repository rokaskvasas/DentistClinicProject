package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.mapper.DoctorEntityMapper;
import eu.codeacademy.projecttooth.tooth.mapper.UserEntityMapper;
import eu.codeacademy.projecttooth.tooth.model.Doctor;
import eu.codeacademy.projecttooth.tooth.model.modelenum.RoleEnum;
import eu.codeacademy.projecttooth.tooth.repository.DoctorEntityRepository;
import eu.codeacademy.projecttooth.tooth.repository.UserEntityRepository;
import eu.codeacademy.projecttooth.tooth.service.DoctorEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorEntityServiceImpl implements DoctorEntityService {


    private final UserEntityMapper userEntityMapper;

    private final DoctorEntityRepository doctorEntityRepository;

    private final DoctorEntityMapper doctorEntityMapper;

    @Override
    public void createDoctor(Doctor doctor) {
        doctorEntityRepository.saveAndFlush(doctorEntityMapper.getDoctorEntity(doctor, userEntityMapper.getUserEntity(doctor, RoleEnum.DOCTOR)));
    }
}
