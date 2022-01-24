package eu.codeacademy.projecttooth.tooth.mapper;

import eu.codeacademy.projecttooth.tooth.dto.ModifyDoctorServiceAvailabilityDto;
import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.entity.DoctorServiceAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.entity.ServiceEntity;
import eu.codeacademy.projecttooth.tooth.model.DoctorServiceAvailability;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorServiceAvailabilityMapper {

    private final DoctorAvailabilityMapper availabilityMapper;

    private final ServiceMapper serviceMapper;

    public DoctorServiceAvailabilityEntity createEntity(ServiceEntity serviceEntity, DoctorAvailabilityEntity availabilityEntity) {
        return DoctorServiceAvailabilityEntity.builder()
                .service(serviceEntity)
                .doctorAvailability(availabilityEntity)
                .build();
    }


    public DoctorServiceAvailability createModel(DoctorServiceAvailabilityEntity entity){
        return DoctorServiceAvailability.builder()
                .doctorAvailability(availabilityMapper.createModel(entity.getDoctorAvailability()))
                .doctorServiceAvailabilityId(entity.getDoctorAvailabilityServiceId())
                .service(serviceMapper.createModel(entity.getService())).build();

    }


}
