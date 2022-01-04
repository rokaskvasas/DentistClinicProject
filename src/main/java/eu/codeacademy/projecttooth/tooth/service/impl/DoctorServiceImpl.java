package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.entity.DoctorEntity;
import eu.codeacademy.projecttooth.tooth.exception.ObjectNotFoundException;
import eu.codeacademy.projecttooth.tooth.mapper.DoctorMapper;
import eu.codeacademy.projecttooth.tooth.mapper.UserMapper;
import eu.codeacademy.projecttooth.tooth.model.Doctor;
import eu.codeacademy.projecttooth.tooth.model.modelenum.RoleEnum;
import eu.codeacademy.projecttooth.tooth.model.modelenum.StatusEnum;
import eu.codeacademy.projecttooth.tooth.repository.DoctorEntityRepository;
import eu.codeacademy.projecttooth.tooth.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {


    private final UserMapper userMapper;

    private final DoctorEntityRepository doctorEntityRepository;

    private final DoctorMapper doctorMapper;

    @Override
    public void createDoctor(Doctor doctor) {
        doctor.setStatus(StatusEnum.UNVERIFIED);
        doctorEntityRepository.saveAndFlush(doctorMapper.createDoctorEntity(doctor, userMapper.getUserEntity(doctor, RoleEnum.DOCTOR)));
    }

    @Override
    public Doctor getDoctor(Long doctorId) {

        return doctorEntityRepository.findDoctorEntityByUserUserId(doctorId).map(doctorMapper::getDoctorModel)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Doctor by id: %s not found", doctorId)));
    }

    @Override
    public void updateDoctor(Doctor doctor) {
        DoctorEntity doctorEntity = doctorEntityRepository.findById(doctor.getDoctorId())
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Doctor by id: %s not found", doctor.getDoctorId())));
        doctorEntityRepository.saveAndFlush(doctorMapper.updateDoctorEntity(doctor, doctorEntity));

    }

    @Override
    @Transactional
    public void deleteDoctor(Doctor doctor) {
        doctorEntityRepository.removeByDoctorId(doctor.getDoctorId());
    }
}
