package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.dto.ModifyDoctorServiceAvailabilityDto;
import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.entity.DoctorServiceAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.entity.ServiceEntity;
import eu.codeacademy.projecttooth.tooth.exception.ObjectNotFoundException;
import eu.codeacademy.projecttooth.tooth.exception.QualificationException;
import eu.codeacademy.projecttooth.tooth.mapper.DoctorServiceAvailabilityMapper;
import eu.codeacademy.projecttooth.tooth.model.DoctorServiceAvailability;
import eu.codeacademy.projecttooth.tooth.model.modelenum.RoleEnum;
import eu.codeacademy.projecttooth.tooth.repository.DoctorAvailabilityRepository;
import eu.codeacademy.projecttooth.tooth.repository.DoctorServiceAvailabilityRepository;
import eu.codeacademy.projecttooth.tooth.repository.ServiceRepository;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;
import eu.codeacademy.projecttooth.tooth.service.DoctorServiceAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class DoctorServiceAvailabilityServiceImpl implements DoctorServiceAvailabilityService {

    private final DoctorServiceAvailabilityMapper mapper;
    private final DoctorAvailabilityRepository availabilityRepository;
    private final ServiceRepository serviceRepository;
    private final DoctorServiceAvailabilityRepository availabilityServiceRepository;
    private final DoctorAvailabilityServiceImpl doctorAvailabilityService;


    @Override
    public Page<DoctorServiceAvailability> getAvailabilityServiceAsPage(UserPrincipal principal, int pageNumber, int pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<DoctorServiceAvailabilityEntity> pageable;

        if (principal.hasRole(RoleEnum.PATIENT) || principal.hasRole(RoleEnum.ADMIN)) {
            pageable = availabilityServiceRepository.findAllAvailable(page);
        } else {
            pageable = availabilityServiceRepository.findAllByUserId(principal.getUserId(), page);
        }
        return pageable.map(mapper::createModel);
    }

    @Override
    public DoctorServiceAvailability getAvailabilityService(Long userId, Long availabilityServiceId) {
        return availabilityServiceRepository.findByUserAndServiceAvailabilityId(userId, availabilityServiceId)
                .map(mapper::createModel)
                .orElseThrow(() -> new ObjectNotFoundException("Doctor Availability service by id not found: " + availabilityServiceId));
    }


    @Override
    public void deleteExpiredServiceAvailability() {
        serviceRepository.deleteAllById(getExpiredServices());
    }


    @Override
    public DoctorServiceAvailability createAvailabilityService(ModifyDoctorServiceAvailabilityDto serviceAvailability, Long userId) {

        isQualified(serviceAvailability, userId);
        Long doctorAvailabilityId = serviceAvailability.getDoctorAvailabilityId();
        ServiceEntity serviceEntity = getServiceEntity(serviceAvailability.getServiceId());
        DoctorAvailabilityEntity doctorAvailabilityEntity = getDoctorAvailabilityEntityByUserID(doctorAvailabilityId, userId);
        return mapper.createModel(availabilityServiceRepository.saveAndFlush(mapper.createEntity(serviceEntity, doctorAvailabilityEntity)));

    }

    @Override
    public DoctorServiceAvailability updateAvailabilityService(ModifyDoctorServiceAvailabilityDto doctorServiceAvailability, Long userId) {
        return mapper.createModel(availabilityServiceRepository.saveAndFlush(updateEntity(doctorServiceAvailability, userId)));
    }

    @Override
    @Transactional
    public Long deleteAvailabilityService(Long serviceId, UserPrincipal principal) {
        if (principal.hasRole(RoleEnum.ADMIN)) {
            availabilityServiceRepository.deleteById(serviceId);
        } else {
            availabilityServiceRepository.delete(getDoctorServiceAvailabilityEntity(serviceId, principal.getUserId()));
        }
        return serviceId;
    }


    private void isQualified(ModifyDoctorServiceAvailabilityDto doctorServiceAvailability, Long userId) {
        int requiredQualification = getServiceEntity(doctorServiceAvailability.getServiceId()).getMinimumQualification().getCourse();
        int currentQualification = doctorAvailabilityService.getDoctorAvailabilityEntity(doctorServiceAvailability.getDoctorAvailabilityId(), userId).getDoctorEntity().getQualification().getCourse();

        if ((requiredQualification > currentQualification)) {
            throw new QualificationException(("Minimum qualification requirements not met."));
        }
    }

    private DoctorAvailabilityEntity getDoctorAvailabilityEntityByUserID(Long availabilityId, Long userId) {
        return availabilityRepository.findByUserAndAvailabilityId(userId, availabilityId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Doctor availability entity by user id:%s not found", availabilityId)));
    }

    private ServiceEntity getServiceEntity(Long serviceId) {
        return serviceRepository.findById(serviceId).
                orElseThrow(() -> new ObjectNotFoundException(String.format("Service entity by id:%s not found", serviceId)));
    }

    private DoctorServiceAvailabilityEntity getDoctorServiceAvailabilityEntity(Long serviceAvailabilityId, Long userId) {
        return availabilityServiceRepository.findByUserAndServiceAvailabilityId(userId, serviceAvailabilityId)
                .orElseThrow(() -> new ObjectNotFoundException("Doctor Service availability entity not found id:" + serviceAvailabilityId));
    }


    private DoctorServiceAvailabilityEntity updateEntity(ModifyDoctorServiceAvailabilityDto doctorServiceAvailability, Long userId) {
        isQualified(doctorServiceAvailability, userId);
        DoctorServiceAvailabilityEntity entity = getDoctorServiceAvailabilityEntity(doctorServiceAvailability.getDoctorServiceAvailabilityId(), userId);
        entity.setService(getServiceEntity(doctorServiceAvailability.getServiceId()));
        return entity;

    }

    private Iterable<Long> getExpiredServices() {
        return () -> availabilityServiceRepository.findAll()
                .stream()
                .filter(service -> service.getDoctorAvailability().getEndTime().isBefore(LocalDateTime.now(ZoneId.systemDefault())))
                .mapToLong(DoctorServiceAvailabilityEntity::getDoctorAvailabilityServiceId).iterator();
    }

}
