package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.entity.DoctorEntity;
import eu.codeacademy.projecttooth.tooth.entity.LocationEntity;
import eu.codeacademy.projecttooth.tooth.exception.ObjectNotFoundException;
import eu.codeacademy.projecttooth.tooth.mapper.DoctorMapper;
import eu.codeacademy.projecttooth.tooth.model.Doctor;
import eu.codeacademy.projecttooth.tooth.model.modelenum.RoleEnum;
import eu.codeacademy.projecttooth.tooth.model.modelenum.StatusEnum;
import eu.codeacademy.projecttooth.tooth.repository.DoctorRepository;
import eu.codeacademy.projecttooth.tooth.security.PasswordService;
import eu.codeacademy.projecttooth.tooth.service.DoctorService;
import eu.codeacademy.projecttooth.tooth.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {


    private final DoctorRepository doctorRepository;

    private final DoctorMapper doctorMapper;

    private final PasswordService passwordService;

    private final LocationService locationService;

    @Override
    public Doctor createDoctor(Doctor doctor) {
        LocationEntity locationEntity = verifyIfLocationExist(doctor);
        DoctorEntity doctorEntity = createDoctorEntity(doctor, locationEntity);
        return createDoctorModel(updateDatabase(doctorEntity));
    }

    @Override
    public Doctor getDoctorByUserId(Long userId) {
        DoctorEntity doctorEntity = findDoctorEntityByUserid(userId);
        return createDoctorModel(doctorEntity);
    }

    @Override
    public Doctor updateDoctor(Doctor doctor, Long userId) {
        DoctorEntity doctorEntity = updateDoctorEntity(doctor, userId);
        updateDatabase(doctorEntity);
        return createDoctorModel(doctorEntity);
    }

    @Override
    @Transactional
    public void deleteDoctor(Long userId) {
        doctorRepository.removeByUser_UserId(userId);
    }

    @Override
    public List<Doctor> getUnverifiedDoctors(String approved, int pageNumber, int pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        return doctorRepository.findAll().stream().filter(e -> e.getStatus().toString().equals(approved)).map(doctorMapper::createModel).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Doctor verifyDoctor(Long doctorId) {
        DoctorEntity doctorEntity = verifyDoctorEntity(doctorId);
        return doctorMapper.createModel(updateDatabase(doctorEntity));
    }

    private DoctorEntity verifyDoctorEntity(Long doctorId) {
        DoctorEntity doctorEntity = findDoctorEntity(doctorId);
        doctorEntity.setStatus(StatusEnum.VERIFIED);
        doctorEntity.getUser().setRole(RoleEnum.ROLE_DOCTOR.determinateRole());
        return doctorEntity;
    }

    private DoctorEntity findDoctorEntity(Long doctorId) {
        return doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Doctor by id: %s not found", doctorId)));
    }

    private DoctorEntity findDoctorEntityByUserid(Long doctorId) {
        return doctorRepository.findDoctorByUserId(doctorId).orElseThrow(() -> new ObjectNotFoundException(String.format("Doctor by id: %s not found", doctorId)));
    }

    private DoctorEntity updateDoctorEntity(Doctor doctor, Long userId) {
        DoctorEntity entity = findDoctorEntityByUserid(userId);
        entity.setQualification(doctor.getQualification());
        entity.setDoctorLicense(doctor.getDoctorLicense());
        entity.getUser().setFirstName(doctor.getFirstName());
        entity.getUser().setLastName(doctor.getLastName());
        entity.getUser().setPhoneNumber(doctor.getPhoneNumber());
        return entity;
    }

    private DoctorEntity createDoctorEntity(Doctor doctor, LocationEntity locationEntity) {
        doctor.setStatus(StatusEnum.UNVERIFIED);
        doctor.setPassword(passwordService.encode(doctor.getPassword()));
        doctor.setRole(RoleEnum.ROLE_UNVERIFIED_DOCTOR.determinateRole());
        doctor.setLocationId(doctor.getLocationId());
        DoctorEntity doctorEntity = doctorMapper.createDoctorEntity(doctor);
        doctorEntity.setLocation(locationEntity);
        return doctorEntity;
    }

    private LocationEntity verifyIfLocationExist(Doctor doctor) {

        LocationEntity locationEntity = locationService.getLocationEntity(doctor.getLocationId());
        if (Objects.isNull(locationEntity)) {

            throw new ObjectNotFoundException(String.format("Location by id%s not found", doctor.getLocationId()));
        }
        return locationEntity;
    }

    private DoctorEntity updateDatabase(DoctorEntity doctorEntity) {
        return doctorRepository.saveAndFlush(doctorEntity);
    }

    private Doctor createDoctorModel(DoctorEntity entity) {
        return doctorMapper.createModel(entity);
    }
}
