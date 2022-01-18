package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.entity.DoctorEntity;
import eu.codeacademy.projecttooth.tooth.entity.LocationEntity;
import eu.codeacademy.projecttooth.tooth.exception.ObjectNotFoundException;
import eu.codeacademy.projecttooth.tooth.mapper.DoctorMapper;
import eu.codeacademy.projecttooth.tooth.mapper.UserMapper;
import eu.codeacademy.projecttooth.tooth.model.Doctor;
import eu.codeacademy.projecttooth.tooth.model.modelenum.RoleEnum;
import eu.codeacademy.projecttooth.tooth.model.modelenum.StatusEnum;
import eu.codeacademy.projecttooth.tooth.repository.DoctorRepository;
import eu.codeacademy.projecttooth.tooth.repository.LocationRepository;
import eu.codeacademy.projecttooth.tooth.security.PasswordService;
import eu.codeacademy.projecttooth.tooth.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    private final LocationRepository locationRepository;

    @Override
    public Doctor createDoctor(Doctor doctor) {
        doctor.setStatus(StatusEnum.UNVERIFIED);
        doctor.setPassword(passwordService.encode(doctor.getPassword()));
        LocationEntity locationEntity = locationRepository.findById(doctor.getLocation().getLocationId()).orElseThrow(() -> new ObjectNotFoundException(String.format("Location by id: %s not found", doctor.getLocation().getLocationId())));
        return doctorMapper.createModel(doctorRepository.saveAndFlush(doctorMapper.createDoctorEntity(doctor, userMapper.getUserEntity(doctor, RoleEnum.UNVERIFIED_DOCTOR), locationEntity)));
    }

    @Override
    public Doctor getDoctor(Long doctorId) {
        return doctorMapper.createModel(getDoctorEntityByUserUserId(doctorId));
    }

    @Override
    public Doctor updateDoctor(Doctor doctor, Long userId) {
        return doctorMapper.createModel(doctorRepository.saveAndFlush(updateDoctorEntity(doctor, userId)));
    }

    @Override
    @Transactional
    public void deleteDoctor(Long userId) {
        doctorRepository.removeByUser_UserId(userId);
    }

    @Override
    public List<Doctor> getUnverifiedDoctors(String approved, int pageNumber, int pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
//        Page<DoctorEntity> pageable = doctorRepository.findAll(page).stream().filter(doctor -> doctor.getStatus().toString().equals(approved));
        return doctorRepository.findAll().stream().filter(e -> e.getStatus().toString().equals(approved)).map(doctorMapper::createModel).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Doctor verifyDoctor(Long doctorId) {
        DoctorEntity doctorEntity = getDoctorEntity(doctorId);
        doctorEntity.setStatus(StatusEnum.VERIFIED);
        doctorEntity.getUser().setRole(RoleEnum.DOCTOR.determinateRole());
        return doctorMapper.createModel(doctorRepository.saveAndFlush(doctorEntity));
    }
    private DoctorEntity getDoctorEntity(Long doctorId) {
        return doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Doctor by id: %s not found", doctorId)));
    }

    private DoctorEntity getDoctorEntityByUserUserId(Long doctorId) {
        return doctorRepository.findDoctor(doctorId).orElseThrow(() -> new ObjectNotFoundException(String.format("Doctor by id: %s not found", doctorId)));
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
