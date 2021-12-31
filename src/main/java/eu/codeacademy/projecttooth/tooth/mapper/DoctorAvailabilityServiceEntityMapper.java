package eu.codeacademy.projecttooth.tooth.mapper;

import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityServiceEntity;
import eu.codeacademy.projecttooth.tooth.entity.ServiceEntity;
import eu.codeacademy.projecttooth.tooth.exception.AvailabilityIdNotFoundException;
import eu.codeacademy.projecttooth.tooth.exception.ServiceByIdNotFoundException;
import eu.codeacademy.projecttooth.tooth.model.DoctorAvailabilityService;
import eu.codeacademy.projecttooth.tooth.repository.DoctorAvailabilityEntityRepository;
import eu.codeacademy.projecttooth.tooth.repository.DoctorAvailabilityServiceEntityRepository;
import eu.codeacademy.projecttooth.tooth.repository.ServiceEntityRepository;
import eu.codeacademy.projecttooth.tooth.service.DoctorAvailabilityEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorAvailabilityServiceEntityMapper {

    private final DoctorAvailabilityEntityRepository availabilityEntityRepository;
    private final ServiceEntityRepository serviceEntityRepository;

    public DoctorAvailabilityServiceEntity getEntity(DoctorAvailabilityService doctorAvailabilityService) {
        var entity = new DoctorAvailabilityServiceEntity();
        entity.setServiceEntity(getServiceEntity(doctorAvailabilityService));
        entity.setDoctorAvailabilityEntity(getDoctorAvailabilityEntity(doctorAvailabilityService));
        return entity;
    }

    private DoctorAvailabilityEntity getDoctorAvailabilityEntity(DoctorAvailabilityService doctorAvailabilityService) {
        return availabilityEntityRepository.findById(doctorAvailabilityService.getDoctorAvailabilityId())
                .orElseThrow(() -> new AvailabilityIdNotFoundException(String.format("Doctor availability by id:%s not found", doctorAvailabilityService.getDoctorAvailabilityId())));
    }

    private ServiceEntity getServiceEntity(DoctorAvailabilityService doctorAvailabilityService) {
        return serviceEntityRepository.findById(doctorAvailabilityService.getServiceId()).
                orElseThrow(() -> new ServiceByIdNotFoundException(String.format("Service by id:%s not found", doctorAvailabilityService.getServiceId())));
    }
}
