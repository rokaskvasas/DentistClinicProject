package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.dto.ModifyDoctorServiceAvailabilityDto;
import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.entity.DoctorServiceAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.entity.ServiceEntity;
import eu.codeacademy.projecttooth.tooth.exception.ObjectNotFoundException;
import eu.codeacademy.projecttooth.tooth.exception.QualificationException;
import eu.codeacademy.projecttooth.tooth.mapper.DoctorServiceAvailabilityMapper;
import eu.codeacademy.projecttooth.tooth.model.DoctorServiceAvailability;
import eu.codeacademy.projecttooth.tooth.model.DoctorScheduler;
import eu.codeacademy.projecttooth.tooth.repository.DoctorAvailabilityRepository;
import eu.codeacademy.projecttooth.tooth.repository.DoctorServiceAvailabilityRepository;
import eu.codeacademy.projecttooth.tooth.repository.ServiceRepository;
import eu.codeacademy.projecttooth.tooth.service.DoctorServiceAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class DoctorServiceAvailabilityServiceImpl implements DoctorServiceAvailabilityService {

    private final DoctorServiceAvailabilityMapper mapper;
    private final DoctorAvailabilityRepository availabilityRepository;
    private final ServiceRepository serviceRepository;
    private final DoctorServiceAvailabilityRepository availabilityServiceRepository;
    private final DoctorAvailabilityServiceImpl doctorAvailabilityService;


    @Override
    public Page<DoctorServiceAvailability> getAvailabilityServiceAsPage(Long userId, int pageNumber, int pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<DoctorServiceAvailabilityEntity> pageable = availabilityServiceRepository.findAllByDoctorAvailabilityDoctorEntityUserUserId(userId, page);
        return pageable.map(mapper::createModel);
    }

    @Override
    public DoctorServiceAvailability getAvailabilityService(Long userId, Long availabilityServiceId) {
        return availabilityServiceRepository.findAllByDoctorAvailabilityDoctorEntityUserUserId(userId)
                .stream()
                .filter(entity -> entity.getDoctorAvailabilityServiceId().equals(availabilityServiceId))
                .findAny()
                .map(mapper::createModel)
                .orElseThrow(() -> new ObjectNotFoundException("Doctor Availability service by id not found: " + availabilityServiceId));
    }

    @Override
    public Page<DoctorServiceAvailability> getAvailabilityServicePageableAsPatient(int pageNumber, int pageSize) {
        Pageable page = PageRequest.of(pageNumber, pageSize);
        Page<DoctorServiceAvailabilityEntity> pageable = availabilityServiceRepository.findAll(page);
        return pageable.map(mapper::createModel);

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
    public void deleteAvailabilityService(Long serviceId, Long userId) {
        availabilityServiceRepository.delete(getDoctorServiceAvailabilityEntity(serviceId, userId));
    }


    private void isQualified(ModifyDoctorServiceAvailabilityDto doctorServiceAvailability, Long userId) {
        int requiredQualification = getServiceEntity(doctorServiceAvailability.getServiceId()).getMinimumQualification().getCourse();
        int currentQualification = doctorAvailabilityService.getDoctorAvailabilityEntity(doctorServiceAvailability.getDoctorAvailabilityId(), userId).getDoctorEntity().getQualification().getCourse();

        if ((requiredQualification > currentQualification)) {
            throw new QualificationException(("Minimum qualification requirements not met."));
        }
    }

    private DoctorAvailabilityEntity getDoctorAvailabilityEntityByUserID(Long doctorAvailabilityId, Long userId) {
        return availabilityRepository.findAllByDoctorEntityUserUserId(userId)
                .stream()
                .filter(entity -> entity.getDoctorAvailabilityId().equals(doctorAvailabilityId))
                .findFirst()
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Doctor availability entity by user id:%s not found", doctorAvailabilityId)));
    }

    private ServiceEntity getServiceEntity(Long serviceId) {
        return serviceRepository.findById(serviceId).
                orElseThrow(() -> new ObjectNotFoundException(String.format("Service entity by id:%s not found", serviceId)));
    }

    private DoctorServiceAvailabilityEntity getDoctorServiceAvailabilityEntity(Long serviceAvailabilityId, Long userId) {
        return availabilityServiceRepository.findAllByDoctorAvailabilityDoctorEntityUserUserId(userId)
                .stream()
                .filter(entity -> entity.getDoctorAvailabilityServiceId().equals(serviceAvailabilityId))
                .findAny()
                .orElseThrow(() -> new ObjectNotFoundException("Doctor Service availability entity not found id:" + serviceAvailabilityId));
    }


    private DoctorServiceAvailabilityEntity updateEntity(ModifyDoctorServiceAvailabilityDto doctorServiceAvailability, Long userId) {
        isQualified(doctorServiceAvailability, userId);
        DoctorServiceAvailabilityEntity entity = getDoctorServiceAvailabilityEntity(doctorServiceAvailability.getDoctorServiceAvailabilityId(), userId);
        entity.setService(getServiceEntity(doctorServiceAvailability.getServiceId()));
        return entity;

    }


    //    @Override
//    public List<DoctorScheduler> getAll(Doctor doctor) {
//        return serviceEntityRepository.findAll().stream().
//                filter(entity -> entity.getDoctorAvailabilityEntity().getDoctorEntity().getDoctorId().equals(doctor.getDoctorId()))
//                .map(this::createScheduler).collect(Collectors.toUnmodifiableList());
//    }


    public DoctorScheduler createScheduler(DoctorServiceAvailabilityEntity entity) {
        return DoctorScheduler.builder()
                .service(entity.getService().getName())
                .startTime(entity.getDoctorAvailability().getStartTime())
                .endTime(entity.getDoctorAvailability().getEndTime())
                .build();
    }
}
