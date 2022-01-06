package eu.codeacademy.projecttooth.tooth.mapper;


import eu.codeacademy.projecttooth.tooth.dto.AppointmentDto;
import eu.codeacademy.projecttooth.tooth.dto.DoctorServiceAvailabilityDto;
import eu.codeacademy.projecttooth.tooth.entity.AppointmentEntity;
import eu.codeacademy.projecttooth.tooth.entity.DoctorServiceAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.entity.PatientEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentMapper {

    public AppointmentEntity createEntity(DoctorServiceAvailabilityDto dto, PatientEntity patient, DoctorServiceAvailabilityEntity serviceAvailabilityEntity) {
        return AppointmentEntity.builder()
                .patient(patient)
                .doctorServiceAvailability(serviceAvailabilityEntity)
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .build();

    }

    public AppointmentDto createDtoModel(AppointmentEntity entity) {
        return AppointmentDto.builder()
                .appointmentId(entity.getAppointmentId())
                .firstName(entity.getDoctorServiceAvailability().getDoctorAvailability().getDoctorEntity().getUser().getFirstName())
                .lastName(entity.getDoctorServiceAvailability().getDoctorAvailability().getDoctorEntity().getUser().getLastName())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime())
                .locationCity(entity.getDoctorServiceAvailability().getDoctorAvailability().getDoctorEntity().getLocation().getCity())
                .locationName(entity.getDoctorServiceAvailability().getDoctorAvailability().getDoctorEntity().getLocation().getName())
                .phoneNumber(entity.getDoctorServiceAvailability().getDoctorAvailability().getDoctorEntity().getUser().getPhoneNumber())
                .serviceEnum(entity.getDoctorServiceAvailability().getService().getName())
                .build();
    }

}
