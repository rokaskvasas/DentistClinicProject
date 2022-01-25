package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.entity.DoctorEntity;
import eu.codeacademy.projecttooth.tooth.exception.IncorrectTimeException;
import eu.codeacademy.projecttooth.tooth.exception.ObjectNotFoundException;
import eu.codeacademy.projecttooth.tooth.mapper.DoctorAvailabilityMapper;
import eu.codeacademy.projecttooth.tooth.model.DoctorAvailability;
import eu.codeacademy.projecttooth.tooth.model.modelenum.RoleEnum;
import eu.codeacademy.projecttooth.tooth.repository.DoctorAvailabilityRepository;
import eu.codeacademy.projecttooth.tooth.repository.DoctorRepository;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;
import eu.codeacademy.projecttooth.tooth.service.DoctorAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class DoctorAvailabilityServiceImpl implements DoctorAvailabilityService {

    private final DoctorAvailabilityMapper mapper;
    private final DoctorAvailabilityRepository availabilityRepository;
    private final DoctorRepository doctorRepository;


    @Override
    public DoctorAvailability getAvailability(Long availabilityId, UserPrincipal principal) {
        return getDoctorAvailability(availabilityId, principal);
    }


    @Override
    public Page<DoctorAvailability> getAvailabilityPageable(UserPrincipal principal, int pageNumber, int pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<DoctorAvailabilityEntity> pageable;
        pageable = getDoctorAvailabilityEntities(principal, page);
        return pageable.map(this::createDoctorAvailabilityModel);
    }


    @Override
    public void deleteExpiredAvailabilities() {
        availabilityRepository.deleteAllById(expiredAvailabilityIds());

    }

    @Override
    public DoctorAvailabilityEntity findDoctorAvailabilityEntityByUserAndAvailabilityId(Long availabilityId, Long userId) {
        return availabilityRepository.findByUserAndAvailabilityId(userId, availabilityId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Doctor availability entity by id:%s not found", availabilityId)));
    }

    @Override
    public DoctorAvailability createAvailability(DoctorAvailability doctorAvailability, Long userId) {
        availabilityTimeCheck(doctorAvailability);
        DoctorAvailabilityEntity doctorAvailabilityEntity = createDoctorAvailabilityEntity(doctorAvailability, userId);
        updateDatabase(doctorAvailabilityEntity);
        return createDoctorAvailabilityModel(doctorAvailabilityEntity);
    }


    @Override
    public DoctorAvailability updateAvailability(DoctorAvailability doctorAvailability, Long userId) {
        return createDoctorAvailabilityModel(availabilityRepository.saveAndFlush(updateEntity(doctorAvailability, userId)));
    }

    @Override
    @Transactional
    public Long deleteAvailability(Long doctorAvailabilityId, UserPrincipal principal) {
        deleteDoctorAvailabilityByPrincipalRole(doctorAvailabilityId, principal);
        return doctorAvailabilityId;
    }

    private void deleteDoctorAvailabilityByPrincipalRole(Long doctorAvailabilityId, UserPrincipal principal) {
        if (principal.hasRole(RoleEnum.ROLE_ADMIN)) {
            availabilityRepository.deleteById(doctorAvailabilityId);
        } else {
            availabilityRepository.delete(getDoctorAvailabilityEntity(doctorAvailabilityId, principal.getUserId()));
        }
    }

    private DoctorAvailabilityEntity updateEntity(DoctorAvailability doctorAvailability, Long userId) {
        availabilityTimeCheck(doctorAvailability);
        DoctorAvailabilityEntity entity = getDoctorAvailabilityEntity(doctorAvailability.getDoctorAvailabilityId(), userId);
        entity.setStartTime(doctorAvailability.getStartTime());
        entity.setEndTime(doctorAvailability.getEndTime());
        return entity;
    }

    private DoctorEntity getDoctorEntityByUserId(Long userId) {
        return doctorRepository.findDoctorByUserId(userId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("DoctorEntity not found by id: %s", userId)));
    }

    @Override
    public DoctorAvailabilityEntity getDoctorAvailabilityEntity(Long doctorAvailabilityId, Long userId) {
        return availabilityRepository.findByUserAndAvailabilityId(userId, doctorAvailabilityId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Doctor availability entity by id:%s not found", userId)));
    }

    private void availabilityTimeCheck(DoctorAvailability doctorAvailability) {
        if (!startAndEndTimeIsCorrect(doctorAvailability)) {
            throw new IncorrectTimeException("StartTime or endTime is incorrect");
        }
        if (!timeIsInSameDay(doctorAvailability)) {
            throw new IncorrectTimeException("Method 'availabilityTimeCheck' dates are not the same");
        }
    }

    private boolean timeIsInSameDay(DoctorAvailability doctorAvailability) {
        return doctorAvailability.getStartTime().toLocalDate().isEqual(doctorAvailability.getEndTime().toLocalDate());
    }

    private boolean startAndEndTimeIsCorrect(DoctorAvailability doctorAvailability) {
        return doctorAvailability.getStartTime().getHour() <= (doctorAvailability.getEndTime().getHour());
    }


    private Iterable<Long> expiredAvailabilityIds() {
        return () -> availabilityRepository.findAll()
                .stream()
                .filter(availability -> availability.getEndTime().isBefore(LocalDateTime.now(ZoneId.systemDefault())))
                .mapToLong(DoctorAvailabilityEntity::getDoctorAvailabilityId).iterator();
    }

    private DoctorAvailabilityEntity updateDatabase(DoctorAvailabilityEntity entity) {
        return availabilityRepository.saveAndFlush(entity);
    }

    private DoctorAvailability createDoctorAvailabilityModel(DoctorAvailabilityEntity entity) {
        return mapper.createModel(entity);
    }

    private DoctorAvailabilityEntity createDoctorAvailabilityEntity(DoctorAvailability doctorAvailability, Long userId) {
        DoctorEntity doctorEntity = getDoctorEntityByUserId(userId);
        DoctorAvailabilityEntity doctorAvailabilityEntity = mapper.createEntity(doctorAvailability);
        doctorAvailabilityEntity.setDoctorEntity(doctorEntity);
        return doctorAvailabilityEntity;
    }

    private Page<DoctorAvailabilityEntity> getDoctorAvailabilityEntities(UserPrincipal principal, Pageable page) {
        Page<DoctorAvailabilityEntity> pageable;
        if (principal.hasRole(RoleEnum.ROLE_ADMIN)) {
            pageable = availabilityRepository.findAll(page);
        } else {
            pageable = availabilityRepository.findAllByUserId(principal.getUserId(), page);
        }
        return pageable;
    }

    private DoctorAvailability getDoctorAvailability(Long availabilityId, UserPrincipal principal) {
        DoctorAvailability doctorAvailability;
        Supplier<ObjectNotFoundException> objectNotFoundExceptionSupplier = () -> new ObjectNotFoundException("Method 'getAvailability' Availability not found, id: " + availabilityId);

        if (principal.hasRole(RoleEnum.ROLE_ADMIN)) {
            doctorAvailability = availabilityRepository.findById(availabilityId).map(mapper::createModel).orElseThrow(objectNotFoundExceptionSupplier);

        } else {
            doctorAvailability = mapper.createModel(availabilityRepository.findByUserAndAvailabilityId(principal.getUserId(), availabilityId)
                    .orElseThrow(objectNotFoundExceptionSupplier));
        }
        return doctorAvailability;
    }
}
