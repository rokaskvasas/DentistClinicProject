package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.dto.DoctorDto;
import eu.codeacademy.projecttooth.tooth.dto.DoctorRegisterDto;
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
import eu.codeacademy.projecttooth.tooth.service.EmailService;
import eu.codeacademy.projecttooth.tooth.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorServiceImpl implements DoctorService {


    private final DoctorRepository doctorRepository;

    private final DoctorMapper doctorMapper;

    private final PasswordService passwordService;

    private final LocationService locationService;

    private final EmailService emailService;


    @Override
    public DoctorRegisterDto createDoctor(Doctor doctor) {
        LocationEntity locationEntity = findLocation(doctor);
        DoctorEntity doctorEntity = createDoctorEntity(doctor, locationEntity);
        sendEmailNotificationAboutNewDoctor(doctor);
        updateDatabase(doctorEntity);
        log.debug("create doctor: {}", doctor);
        return createDoctorRegisterModel(doctorEntity);
    }

    private DoctorRegisterDto createDoctorRegisterModel(DoctorEntity entity) {
        return doctorMapper.createRegisterDtoModel(entity);
    }


    @Override
    public DoctorRegisterDto getDoctorByUserId(Long userId) {
        DoctorEntity doctorEntity = findDoctorEntityByUserid(userId);
        return createDoctorRegisterModel(doctorEntity);
    }

    @Override
    public DoctorRegisterDto updateDoctor(DoctorDto doctorDto, Long userId) {
        DoctorEntity doctorEntity = updateDoctorEntity(doctorDto, userId);
        updateDatabase(doctorEntity);
        log.debug("Updated doctor: {}", doctorDto);
        return createDoctorRegisterModel(doctorEntity);
    }

    @Override
    @Transactional
    public Long deleteDoctor(Long userId) {
        DoctorEntity doctorEntity = findDoctorEntity(userId);
        doctorRepository.delete(doctorEntity);
        log.debug("Deleted doctor by userId:{}", userId);
        return doctorEntity.getDoctorId();
    }

    @Override
    public Page<Doctor> findAllDoctorsByStatus(String status, int pageNumber, int pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<DoctorEntity> pageable = findAllDoctorsByStatus(status, page);
        return pageable.map(this::createDoctorModel);
    }


    @Override
    public DoctorRegisterDto verifyDoctor(Long doctorId) {
        DoctorEntity doctorEntity = verifyDoctorEntity(doctorId);
        sendVerificationEmailToDoctor(doctorEntity);
        updateDatabase(doctorEntity);
        log.debug("Verified doctor by id:{}", doctorId);
        return createDoctorRegisterModel(doctorEntity);
    }

    @Override
    public List<DoctorEntity> findAllDoctorsWithSpecification(Specification<DoctorEntity> specification) {
        return doctorRepository.findAll(specification);
    }

    @Override
    public void deleteUnverifiedDoctors(Iterable<DoctorEntity> expiredDoctors) {
        doctorRepository.deleteAllInBatch(expiredDoctors);
    }


    private DoctorEntity verifyDoctorEntity(Long doctorId) {
        DoctorEntity doctorEntity = findDoctorEntity(doctorId);
        doctorEntity.setStatus(StatusEnum.VERIFIED);
        doctorEntity.getUser().setRole(RoleEnum.ROLE_DOCTOR.determinateRole());
        return doctorEntity;
    }

    @Override
    public DoctorEntity findDoctorEntity(Long userId) {
        return doctorRepository.findDoctorByUserId(userId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Doctor by user id: %s not found", userId)));
    }

    private DoctorEntity findDoctorEntityByUserid(Long userId) {
        return doctorRepository.findDoctorByUserId(userId).orElseThrow(() -> new ObjectNotFoundException(String.format("Doctor by user id: %s not found", userId)));
    }

    private DoctorEntity updateDoctorEntity(DoctorDto doctorDto, Long userId) {
        DoctorEntity entity = findDoctorEntityByUserid(userId);
        entity.setQualification(doctorDto.getQualification());
        entity.setDoctorLicense(doctorDto.getDoctorLicense());
        entity.getUser().setFirstName(doctorDto.getFirstName());
        entity.getUser().setLastName(doctorDto.getLastName());
        entity.getUser().setPhoneNumber(doctorDto.getPhoneNumber());
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

    private LocationEntity findLocation(Doctor doctor) {
        return locationService.getLocationEntity(doctor.getLocationId());
    }

    private DoctorEntity updateDatabase(DoctorEntity doctorEntity) {
        return doctorRepository.saveAndFlush(doctorEntity);
    }

    private Doctor createDoctorModel(DoctorEntity entity) {
        return doctorMapper.createModel(entity);
    }

    private Page<DoctorEntity> findAllDoctorsByStatus(String status, Pageable page) {
        Page<DoctorEntity> pageable;
        if (status.equals("UNVERIFIED")) {
            pageable = doctorRepository.findAllDoctorsByStatus(StatusEnum.UNVERIFIED, page);
        } else {
            pageable = doctorRepository.findAllDoctorsByStatus(StatusEnum.VERIFIED, page);
        }
        return pageable;
    }

    private void sendVerificationEmailToDoctor(DoctorEntity doctorEntity) {
        emailService.send(doctorEntity.getUser().getEmail(), "Congratulations you've been verified", "Verification");
    }

    private void sendEmailNotificationAboutNewDoctor(Doctor doctor) {
        emailService.send("DocBot@dentistClinic.com",
                String.format("new doctor with email: %s and license %s waiting for verification", doctor.getEmail(), doctor.getDoctorLicense()),
                "New doctor register");
    }


}
