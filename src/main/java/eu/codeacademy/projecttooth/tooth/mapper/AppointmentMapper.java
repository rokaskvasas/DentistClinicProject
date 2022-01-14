package eu.codeacademy.projecttooth.tooth.mapper;


import eu.codeacademy.projecttooth.tooth.dto.ModifyAppointmentDto;
import eu.codeacademy.projecttooth.tooth.entity.*;

import eu.codeacademy.projecttooth.tooth.model.Appointment;
import eu.codeacademy.projecttooth.tooth.model.DoctorServiceAvailability;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentMapper {

    private final PatientMapper patientMapper;

    private final DoctorServiceAvailabilityMapper serviceAvailabilityMapper;

    public AppointmentEntity createEntity(ModifyAppointmentDto doctorServiceAvailability, PatientEntity patient, DoctorServiceAvailabilityEntity serviceAvailabilityEntity) {
        return AppointmentEntity.builder()
                .patient(patient)
                .doctorServiceAvailability(serviceAvailabilityEntity)
                .startTime(doctorServiceAvailability.getStartTime())
                .endTime(doctorServiceAvailability.getEndTime())
                .build();

    }

    public Appointment createDtoModel(AppointmentEntity entity){
        Appointment.AppointmentBuilder builder = Appointment.builder()
                .appointmentId(entity.getAppointmentId())
                .startTime(entity.getStartTime())
                .endTime(entity.getEndTime());
        builder.patient(patientMapper.createModel(entity.getPatient()))
                .serviceAvailability(serviceAvailabilityMapper.createModel(entity.getDoctorServiceAvailability()));
        return builder.build();

    }

}
