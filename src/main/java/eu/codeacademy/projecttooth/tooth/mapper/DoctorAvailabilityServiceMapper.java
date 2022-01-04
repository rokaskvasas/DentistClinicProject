package eu.codeacademy.projecttooth.tooth.mapper;

import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityServiceEntity;
import eu.codeacademy.projecttooth.tooth.entity.ServiceEntity;
import eu.codeacademy.projecttooth.tooth.exception.ObjectNotFoundException;
import eu.codeacademy.projecttooth.tooth.exception.QualificationException;
import eu.codeacademy.projecttooth.tooth.model.DoctorAvailabilityService;
import eu.codeacademy.projecttooth.tooth.repository.DoctorAvailabilityEntityRepository;
import eu.codeacademy.projecttooth.tooth.repository.DoctorAvailabilityServiceEntityRepository;
import eu.codeacademy.projecttooth.tooth.repository.ServiceEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorAvailabilityServiceMapper {

    private final DoctorAvailabilityEntityRepository availabilityEntityRepository;
    private final ServiceEntityRepository serviceEntityRepository;
    private final DoctorAvailabilityServiceEntityRepository availabilityServiceEntityRepository;

    public DoctorAvailabilityServiceEntity createEntity(DoctorAvailabilityService doctorAvailabilityService) {
        isQualified(doctorAvailabilityService);
        return DoctorAvailabilityServiceEntity.builder()
                .serviceEntity(getServiceEntity(doctorAvailabilityService))
                .doctorAvailabilityEntity(getDoctorAvailabilityEntity(doctorAvailabilityService))
                .build();
    }

    public DoctorAvailabilityService createModel(DoctorAvailabilityServiceEntity entity){
        return DoctorAvailabilityService.builder()
                .doctorAvailabilityServiceId(entity.getDoctorAvailabilityServiceId())
                .serviceId(entity.getServiceEntity().getServiceId())
                .doctorAvailabilityId(entity.getDoctorAvailabilityEntity().getDoctorAvailabilityId())
                .build();
    }

    private void isQualified(DoctorAvailabilityService doctorAvailabilityService) {
        if ((getServiceEntity(doctorAvailabilityService).getMinimumQualification().getCourse() > getDoctorAvailabilityEntity(doctorAvailabilityService).getDoctorEntity().getQualification().getCourse())) {
            throw new QualificationException(("Minimum qualification requirements not enough."));
        }
    }

    private DoctorAvailabilityEntity getDoctorAvailabilityEntity(DoctorAvailabilityService doctorAvailabilityService) {
        return availabilityEntityRepository.findById(doctorAvailabilityService.getDoctorAvailabilityId())
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Doctor availability by id:%s not found", doctorAvailabilityService.getDoctorAvailabilityId())));
    }

    private ServiceEntity getServiceEntity(DoctorAvailabilityService doctorAvailabilityService) {
        return serviceEntityRepository.findById(doctorAvailabilityService.getServiceId()).
                orElseThrow(() -> new ObjectNotFoundException(String.format("Service by id:%s not found", doctorAvailabilityService.getServiceId())));
    }


    public DoctorAvailabilityServiceEntity updateEntity(DoctorAvailabilityService doctorAvailabilityService) {
        DoctorAvailabilityServiceEntity entity = availabilityServiceEntityRepository.findById(doctorAvailabilityService.getDoctorAvailabilityServiceId())
                .orElseThrow(() -> new ObjectNotFoundException(String.format("Availability service by id:%s not found", doctorAvailabilityService.getDoctorAvailabilityId())));
        isQualified(doctorAvailabilityService);
        entity.setServiceEntity(getServiceEntity(doctorAvailabilityService));
        return entity;
    }
}
