package eu.codeacademy.projecttooth.tooth.mapper;

import eu.codeacademy.projecttooth.tooth.dto.ModifyDoctorServiceAvailabilityDto;
import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.entity.DoctorEntity;
import eu.codeacademy.projecttooth.tooth.model.DoctorAvailability;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorAvailabilityMapper {

    private final DoctorMapper doctorMapper;

    public DoctorAvailabilityEntity createEntity(DoctorAvailability doctorAvailability,DoctorEntity entity) {
        return DoctorAvailabilityEntity.builder()
                .startTime(doctorAvailability.getStartTime())
                .endTime(doctorAvailability.getEndTime())
                .doctorEntity(entity)
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



}
