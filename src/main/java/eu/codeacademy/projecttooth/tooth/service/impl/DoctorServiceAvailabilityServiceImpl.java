package eu.codeacademy.projecttooth.tooth.service.impl;

import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.entity.DoctorServiceAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.entity.ServiceEntity;
import eu.codeacademy.projecttooth.tooth.exception.ObjectNotFoundException;
import eu.codeacademy.projecttooth.tooth.exception.QualificationException;
import eu.codeacademy.projecttooth.tooth.mapper.DoctorServiceAvailabilityMapper;
import eu.codeacademy.projecttooth.tooth.model.DoctorAvailability;
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

    private final DoctorServiceAvailabilityMapper serviceAvailabilityMapper;
    private final DoctorAvailabilityRepository availabilityRepository;
    private final ServiceRepository serviceRepository;
    private final DoctorServiceAvailabilityRepository availabilityServiceRepository;

    @Override
    public void createAvailabilityService(DoctorServiceAvailability serviceAvailability, Long userId) {
        isQualified(serviceAvailability);
        availabilityServiceRepository.saveAndFlush(serviceAvailabilityMapper
                .createEntity(getServiceEntity(serviceAvailability.getServiceId()),getDoctorAvailabilityEntityByUserID(serviceAvailability.getDoctorAvailabilityId(),userId)));

    }

    @Override
    public List<DoctorServiceAvailability> getAvailabilityServiceList(Long userId) {
        return availabilityServiceRepository
                .findAllByDoctorAvailabilityEntityDoctorEntityUserUserId(userId)
                .stream().map(serviceAvailabilityMapper::createModel).collect(Collectors.toUnmodifiableList());
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

    @Override
    public DoctorServiceAvailability getAvailabilityService(Long userId, Long availabilityServiceId) {
        return availabilityServiceRepository.findAllByDoctorAvailabilityEntityDoctorEntityUserUserId(userId)
                .stream().filter(entity -> entity.getDoctorAvailabilityServiceId().equals(availabilityServiceId))
                .findAny().map(serviceAvailabilityMapper::createModel)
                .orElseThrow(()-> new ObjectNotFoundException("Doctor Availability service by id not found: "+ availabilityServiceId));
    }

    private void isQualified(DoctorServiceAvailability doctorServiceAvailability) {
        if ((getServiceEntity(doctorServiceAvailability.getServiceId()).getMinimumQualification().getCourse() > getDoctorAvailabilityEntity(doctorServiceAvailability.getDoctorAvailabilityId()).getDoctorEntity().getQualification().getCourse())) {
            throw new QualificationException(("Minimum qualification requirements not meet."));
        }
    }

    private DoctorAvailabilityEntity getDoctorAvailabilityEntity(Long availabilityId) {
        return availabilityRepository.findById(availabilityId)
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Doctor availability entity by id:%s not found", availabilityId)));
    }

    private DoctorAvailabilityEntity getDoctorAvailabilityEntityByUserID(Long doctorAvailabilityId, Long userId){
        return availabilityRepository.findAllByDoctorEntityUserUserId(userId).stream()
                .filter(entity -> entity.getDoctorAvailabilityId().equals(doctorAvailabilityId)).findFirst().orElseThrow(()-> new ObjectNotFoundException("getDoctorAvailabilityEntityByUserID bad id"));
    }

    private ServiceEntity getServiceEntity(Long serviceId) {
        return serviceRepository.findById(serviceId).
                orElseThrow(() -> new ObjectNotFoundException(String.format("Service entity by id:%s not found", serviceId)));
    }

    private DoctorServiceAvailabilityEntity getDoctorServiceAvailabilityEntity(Long serviceAvailabilityId, Long userId){
        return availabilityServiceRepository.findAllByDoctorAvailabilityEntityDoctorEntityUserUserId(userId)
                .stream().filter(entity -> entity.getDoctorAvailabilityServiceId().equals(serviceAvailabilityId)).findAny()
                .orElseThrow(()-> new ObjectNotFoundException("Doctor Service availability entity not found id:"+serviceAvailabilityId));
    }


    public DoctorServiceAvailabilityEntity updateEntity(DoctorServiceAvailability doctorServiceAvailability, Long userId) {
        isQualified(doctorServiceAvailability);
        DoctorServiceAvailabilityEntity entity = getDoctorServiceAvailabilityEntity(doctorServiceAvailability.getDoctorAvailabilityServiceId(), userId);
        entity.setServiceEntity(getServiceEntity(doctorServiceAvailability.getServiceId()));
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
                .service(entity.getServiceEntity().getName())
                .startTime(entity.getDoctorAvailabilityEntity().getStartTime())
                .endTime(entity.getDoctorAvailabilityEntity().getEndTime())
                .build();
    }
}