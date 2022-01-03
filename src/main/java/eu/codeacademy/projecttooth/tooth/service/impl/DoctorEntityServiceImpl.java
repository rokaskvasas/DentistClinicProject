package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.entity.DoctorEntity;
import eu.codeacademy.projecttooth.tooth.exception.IdNotFoundException;
import eu.codeacademy.projecttooth.tooth.mapper.DoctorEntityMapper;
import eu.codeacademy.projecttooth.tooth.mapper.UserEntityMapper;
import eu.codeacademy.projecttooth.tooth.model.Doctor;
import eu.codeacademy.projecttooth.tooth.model.modelenum.RoleEnum;
import eu.codeacademy.projecttooth.tooth.repository.DoctorEntityRepository;
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
        doctorEntityRepository.saveAndFlush(doctorEntityMapper.createDoctorEntity(doctor, userEntityMapper.getUserEntity(doctor, RoleEnum.DOCTOR)));
    }

    @Override
    public Doctor getDoctor(Long doctorId) {

        return doctorEntityRepository.findDoctorEntityByUserUserId(doctorId).map(doctorEntityMapper::getDoctorModel)
                .orElseThrow(() -> new IdNotFoundException(String.format("Doctor by id: %s not found", doctorId)));
    }

    @Override
    public void updateDoctor(Doctor doctor) {
        DoctorEntity doctorEntity = doctorEntityRepository.findById(doctor.getDoctorId())
                .orElseThrow(() -> new IdNotFoundException(String.format("Doctor by id: %s not found", doctor.getDoctorId())));
        doctorEntityRepository.saveAndFlush(doctorEntityMapper.updateDoctorEntity(doctor, doctorEntity));

    }
}
