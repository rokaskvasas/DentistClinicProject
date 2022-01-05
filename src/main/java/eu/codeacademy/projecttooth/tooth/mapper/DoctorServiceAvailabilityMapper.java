package eu.codeacademy.projecttooth.tooth.mapper;

import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.entity.DoctorServiceAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.entity.ServiceEntity;
import eu.codeacademy.projecttooth.tooth.model.DoctorServiceAvailability;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorServiceAvailabilityMapper {



    public DoctorServiceAvailabilityEntity createEntity(ServiceEntity serviceEntity, DoctorAvailabilityEntity availabilityEntity) {
        return DoctorServiceAvailabilityEntity.builder()
                .serviceEntity(serviceEntity)
                .doctorAvailabilityEntity(availabilityEntity)
                .build();
    }

    public DoctorServiceAvailability createModel(DoctorServiceAvailabilityEntity entity){
        return DoctorServiceAvailability.builder()
                .doctorAvailabilityServiceId(entity.getDoctorAvailabilityServiceId())
                .serviceId(entity.getServiceEntity().getServiceId())
                .doctorAvailabilityId(entity.getDoctorAvailabilityEntity().getDoctorAvailabilityId())
                .build();
    }


}
