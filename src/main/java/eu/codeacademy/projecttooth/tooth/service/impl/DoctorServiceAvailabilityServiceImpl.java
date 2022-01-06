package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.dto.DoctorServiceAvailabilityDto;
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
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorServiceAvailabilityServiceImpl implements DoctorServiceAvailabilityService {

    private final DoctorServiceAvailabilityMapper mapper;
    private final DoctorAvailabilityRepository availabilityRepository;
    private final ServiceRepository serviceRepository;
    private final DoctorServiceAvailabilityRepository availabilityServiceRepository;


    @Override
    public List<DoctorServiceAvailability> getAvailabilityServiceList(Long userId) {
        return availabilityServiceRepository
                .findAllByDoctorAvailabilityDoctorEntityUserUserId(userId)
                .stream()
                .map(mapper::createModel).collect(Collectors.toUnmodifiableList());
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
    public List<DoctorServiceAvailabilityDto> getAvailabilityServiceListAsPatient() {
        return availabilityServiceRepository.findAll()
                .stream()
                .map(mapper::createDtoModel)
                .collect(Collectors.toUnmodifiableList());

    }

    @Override
    public void createAvailabilityService(DoctorServiceAvailability serviceAvailability, Long userId) {
        isQualified(serviceAvailability);
        ServiceEntity serviceEntity = getServiceEntity(serviceAvailability.getServiceId());
        DoctorAvailabilityEntity doctorAvailabilityEntity = getDoctorAvailabilityEntityByUserID(serviceAvailability.getDoctorAvailabilityId(), userId);
        availabilityServiceRepository.saveAndFlush(mapper.createEntity(serviceEntity, doctorAvailabilityEntity));

    }

    @Override
    public void updateAvailabilityService(DoctorServiceAvailability doctorServiceAvailability, Long userId) {
        availabilityServiceRepository.saveAndFlush(updateEntity(doctorServiceAvailability, userId));
    }

    @Override
    @Transactional
    public void deleteAvailabilityService(Long serviceId, Long userId) {
        availabilityServiceRepository.delete(getDoctorServiceAvailabilityEntity(serviceId, userId));
    }


    private void isQualified(DoctorServiceAvailability doctorServiceAvailability) {
        int requiredQualification = getServiceEntity(doctorServiceAvailability.getServiceId()).getMinimumQualification().getCourse();
        int currentQualification = getDoctorAvailabilityEntity(doctorServiceAvailability.getDoctorAvailabilityId()).getDoctorEntity().getQualification().getCourse();

        if ((requiredQualification > currentQualification)) {
            throw new QualificationException(("Minimum qualification requirements not met."));
        }
    }

    private DoctorAvailabilityEntity getDoctorAvailabilityEntity(Long availabilityId) {
        return availabilityRepository.findById(availabilityId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Doctor availability entity by id:%s not found", availabilityId)));
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


    public DoctorServiceAvailabilityEntity updateEntity(DoctorServiceAvailability doctorServiceAvailability, Long userId) {
        isQualified(doctorServiceAvailability);
        DoctorServiceAvailabilityEntity entity = getDoctorServiceAvailabilityEntity(doctorServiceAvailability.getDoctorAvailabilityServiceId(), userId);
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
