package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.dto.DoctorDto;
import eu.codeacademy.projecttooth.tooth.entity.DoctorEntity;
import eu.codeacademy.projecttooth.tooth.exception.ObjectNotFoundException;
import eu.codeacademy.projecttooth.tooth.mapper.DoctorMapper;
import eu.codeacademy.projecttooth.tooth.mapper.UserMapper;
import eu.codeacademy.projecttooth.tooth.model.Doctor;
import eu.codeacademy.projecttooth.tooth.model.modelenum.RoleEnum;
import eu.codeacademy.projecttooth.tooth.model.modelenum.StatusEnum;
import eu.codeacademy.projecttooth.tooth.repository.DoctorRepository;
import eu.codeacademy.projecttooth.tooth.security.PasswordService;
import eu.codeacademy.projecttooth.tooth.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {


    private final UserMapper userMapper;

    private final DoctorRepository doctorRepository;

    private final DoctorMapper doctorMapper;

    private final PasswordService passwordService;

    @Override
    public void createDoctor(Doctor doctor) {
        doctor.setStatus(StatusEnum.UNVERIFIED);
        doctor.setPassword(passwordService.passwordEncoder().encode(doctor.getPassword()));
        doctorRepository.saveAndFlush(doctorMapper.createDoctorEntity(doctor, userMapper.getUserEntity(doctor, RoleEnum.DOCTOR)));
    }

    @Override
    public DoctorDto getDoctor(Long doctorId) {
        return doctorMapper.createDtoModel(getDoctorEntityByUserUserId(doctorId));
    }

    @Override
    public void updateDoctor(Doctor doctor, Long userId) {
        doctorRepository.saveAndFlush(updateDoctorEntity(doctor, userId));
    }

    @Override
    @Transactional
    public void deleteDoctor(Long userId) {
        doctorRepository.removeByUser_UserId(userId);
    }

    @Override
    public List<Doctor> getDoctorList(String approved) {
        return doctorRepository.findAll().stream().filter(e -> e.getStatus().toString().equals(approved)).map(doctorMapper::createDoctorModel).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void verifyDoctor(Long doctorId) {
        DoctorEntity doctorEntity = getDoctorEntity(doctorId);
        doctorEntity.setStatus(StatusEnum.VERIFIED);
        doctorRepository.saveAndFlush(doctorEntity);
    }

    private DoctorEntity getDoctorEntity(Long doctorId) {
        return doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Doctor by id: %s not found", doctorId)));
    }

    private DoctorEntity getDoctorEntityByUserUserId(Long doctorId) {
        return doctorRepository.findDoctorEntityByUserUserId(doctorId).orElseThrow(() -> new ObjectNotFoundException(String.format("Doctor by id: %s not found", doctorId)));
    }

    private DoctorEntity updateDoctorEntity(Doctor doctor, Long userId) {
        DoctorEntity entity = getDoctorEntityByUserUserId(userId);
        entity.setQualification(doctor.getQualification());
        entity.setDoctorLicense(doctor.getDoctorLicense());
        entity.getUser().setFirstName(doctor.getFirstName());
        entity.getUser().setLastName(doctor.getLastName());
        entity.getUser().setPhoneNumber(doctor.getPhoneNumber());
        return entity;
    }
}
