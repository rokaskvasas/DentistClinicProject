package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.dto.DoctorAvailabilityDto;
import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.entity.DoctorEntity;
import eu.codeacademy.projecttooth.tooth.exception.IncorrectTimeException;
import eu.codeacademy.projecttooth.tooth.exception.ObjectNotFoundException;
import eu.codeacademy.projecttooth.tooth.mapper.DoctorAvailabilityMapper;
import eu.codeacademy.projecttooth.tooth.model.modelenum.RoleEnum;
import eu.codeacademy.projecttooth.tooth.repository.DoctorAvailabilityRepository;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;
import eu.codeacademy.projecttooth.tooth.service.DoctorAvailabilityService;
import eu.codeacademy.projecttooth.tooth.service.DoctorService;
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
    private final DoctorService doctorService;


    @Override
    public DoctorAvailabilityDto getAvailability(Long availabilityId, UserPrincipal principal) {
        return getDoctorAvailability(availabilityId, principal);
    }

    @Override
    public Page<DoctorAvailabilityDto> getAvailabilityPageable(UserPrincipal principal, int pageNumber, int pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<DoctorAvailabilityEntity> pageable;
        pageable = getDoctorAvailabilityEntities(principal, page);
        return pageable.map(this::createDoctorAvailabilityDtoModel);
    }


    @Override
    public void deleteExpiredAvailabilities() {
        availabilityRepository.deleteAllById(expiredAvailabilityIds());

    }

    @Override
    public DoctorAvailabilityEntity findDoctorAvailabilityEntityByUserAndAvailabilityId(Long availabilityId, Long userId) {
        return availabilityRepository.findByUserAndAvailabilityId(userId, availabilityId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Doctor availability entity by id:%s not found!", availabilityId)));
    }

    @Override
    public DoctorAvailabilityDto createAvailability(DoctorAvailabilityDto doctorAvailabilityDto, Long userId) {
        availabilityTimeCheck(doctorAvailabilityDto);
        availabilityDuplicateCheck(doctorAvailabilityDto, userId);
        DoctorAvailabilityEntity doctorAvailabilityEntity = createDoctorAvailabilityEntity(doctorAvailabilityDto, userId);
        updateDatabase(doctorAvailabilityEntity);
        return createDoctorAvailabilityDtoModel(doctorAvailabilityEntity);
    }


    @Override
    public DoctorAvailabilityDto updateAvailability(DoctorAvailabilityDto doctorAvailabilityDto, Long userId) {
        availabilityDuplicateCheck(doctorAvailabilityDto,userId);
        DoctorAvailabilityEntity doctorAvailabilityEntity = updateEntity(doctorAvailabilityDto, userId);
        updateDatabase(doctorAvailabilityEntity);
        return createDoctorAvailabilityDtoModel(doctorAvailabilityEntity);
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

    private DoctorAvailabilityEntity updateEntity(DoctorAvailabilityDto doctorAvailability, Long userId) {
        availabilityTimeCheck(doctorAvailability);
        DoctorAvailabilityEntity entity = getDoctorAvailabilityEntity(doctorAvailability.getDoctorAvailabilityId(), userId);
        entity.setStartTime(doctorAvailability.getStartTime());
        entity.setEndTime(doctorAvailability.getEndTime());
        return entity;
    }

    private DoctorEntity findDoctorEntityByUserId(Long userId) {
        return doctorService.findDoctorEntity(userId);
    }

    @Override
    public DoctorAvailabilityEntity getDoctorAvailabilityEntity(Long doctorAvailabilityId, Long userId) {
        return availabilityRepository.findByUserAndAvailabilityId(userId, doctorAvailabilityId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Doctor availability entity by id:%s not found!", userId)));
    }

    private void availabilityTimeCheck(DoctorAvailabilityDto doctorAvailabilityDto) {
        if (!startAndEndTimeIsCorrect(doctorAvailabilityDto)) {
            throw new IncorrectTimeException("StartTime or endTime is incorrect!");
        }
        if (!timeIsInSameDay(doctorAvailabilityDto)) {
            throw new IncorrectTimeException("Please choose same day!");
        }
    }

    private boolean timeIsInSameDay(DoctorAvailabilityDto doctorAvailability) {
        return doctorAvailability.getStartTime().toLocalDate().isEqual(doctorAvailability.getEndTime().toLocalDate());
    }

    private boolean startAndEndTimeIsCorrect(DoctorAvailabilityDto doctorAvailabilityDto) {
        return doctorAvailabilityDto.getStartTime().getHour() < (doctorAvailabilityDto.getEndTime().getHour());
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


    private DoctorAvailabilityDto createDoctorAvailabilityDtoModel(DoctorAvailabilityEntity entity) {
        return mapper.createDtoModel(entity);
    }

    private DoctorAvailabilityEntity createDoctorAvailabilityEntity(DoctorAvailabilityDto doctorAvailability, Long userId) {
        DoctorEntity doctorEntity = findDoctorEntityByUserId(userId);
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

    private DoctorAvailabilityDto getDoctorAvailability(Long availabilityId, UserPrincipal principal) {
        DoctorAvailabilityDto doctorAvailabilityDto;
        Supplier<ObjectNotFoundException> objectNotFoundExceptionSupplier = () -> new ObjectNotFoundException("Availability not found, id: " + availabilityId);

        if (principal.hasRole(RoleEnum.ROLE_ADMIN)) {
            doctorAvailabilityDto = availabilityRepository.findById(availabilityId).map(this::createDoctorAvailabilityDtoModel).orElseThrow(objectNotFoundExceptionSupplier);

        } else {
            doctorAvailabilityDto = availabilityRepository.findByUserAndAvailabilityId(principal.getUserId(), availabilityId).map(this::createDoctorAvailabilityDtoModel)
                    .orElseThrow(objectNotFoundExceptionSupplier);
        }
        return doctorAvailabilityDto;
    }

    private void availabilityDuplicateCheck(DoctorAvailabilityDto doctorAvailability, Long userId) {
        LocalDateTime startTime = doctorAvailability.getStartTime();
        if (availabilityRepository.findByUserIdAndStartTime(userId, startTime).isPresent()) {
            throw new IncorrectTimeException("Doctor availability with this time already exists!");
        }
    }
}
