package eu.codeacademy.projecttooth.tooth.mapper;

import eu.codeacademy.projecttooth.tooth.dto.DoctorAvailabilityDto;
import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.model.DoctorAvailability;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorAvailabilityMapper {

    private final DoctorMapper doctorMapper;

    public DoctorAvailabilityEntity createEntity(DoctorAvailabilityDto doctorAvailability) {
        return DoctorAvailabilityEntity.builder()
                .startTime(doctorAvailability.getStartTime())
                .endTime(doctorAvailability.getEndTime())
                .build();
    }

    public DoctorAvailability createModel(DoctorAvailabilityEntity entity) {
        return DoctorAvailability.builder()
                .doctorAvailabilityId(entity.getDoctorAvailabilityId())
                .doctor(doctorMapper.createModel(entity.getDoctorEntity()))
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .build();
    }

    public DoctorAvailabilityDto createDtoModel(DoctorAvailabilityEntity entity) {
        return DoctorAvailabilityDto.builder()
                .doctorAvailabilityId(entity.getDoctorAvailabilityId())
                .startTime(entity.getStartTime())
                .doctorDto(doctorMapper.createDtoModel(entity.getDoctorEntity()))
                .endTime(entity.getEndTime()).build();
    }
}
