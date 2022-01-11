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
                .service(serviceEntity)
                .doctorAvailability(availabilityEntity)
                .build();
    }

    public DoctorServiceAvailability createModel(DoctorServiceAvailabilityEntity entity){
        return DoctorServiceAvailability.builder()
                .doctorAvailabilityServiceId(entity.getDoctorAvailabilityServiceId())
                .serviceId(entity.getService().getServiceId())
                .doctorAvailabilityId(entity.getDoctorAvailability().getDoctorAvailabilityId())
                .build();
    }

    public DoctorServiceAvailabilityDto createDtoModel(DoctorServiceAvailabilityEntity entity){
        return DoctorServiceAvailabilityDto.builder()
                .firstName(entity.getDoctorAvailability().getDoctorEntity().getUser().getFirstName())
                .lastName(entity.getDoctorAvailability().getDoctorEntity().getUser().getLastName())
                .startTime(entity.getDoctorAvailability().getStartTime())
                .endTime(entity.getDoctorAvailability().getEndTime())
                .locationName(entity.getDoctorAvailability().getDoctorEntity().getLocation().getName())
                .locationCity(entity.getDoctorAvailability().getDoctorEntity().getLocation().getCity())
                .serviceEnum(entity.getService().getName())
                .doctorServiceAvailabilityId(entity.getDoctorAvailabilityServiceId())
                .build();
    }

}
