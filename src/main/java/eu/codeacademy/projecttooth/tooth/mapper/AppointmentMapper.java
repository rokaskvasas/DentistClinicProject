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

//    public AppointmentDto createDtoModel(AppointmentEntity entity) {
//        DoctorServiceAvailabilityEntity doctorServiceAvailability = entity.getDoctorServiceAvailability();
//        ServiceEntity service = doctorServiceAvailability.getService();
//        DoctorAvailabilityEntity doctorAvailability = doctorServiceAvailability.getDoctorAvailability();
//        DoctorEntity doctorEntity = doctorAvailability.getDoctorEntity();
//        LocationEntity location = doctorEntity.getLocation();
//        UserEntity user = doctorEntity.getUser();
//
//        checkIfNullIsPresent("Creating appointmentDTO", doctorAvailability, doctorServiceAvailability, doctorEntity, location, service, user);
//
//        return AppointmentDto.builder()
//                .appointmentId(entity.getAppointmentId())
//                .startTime(entity.getStartTime())
//                .endTime(entity.getEndTime())
//                .firstName(user.getFirstName())
//                .lastName(user.getLastName())
//                .locationCity(location.getCity())
//                .locationName(location.getName())
//                .phoneNumber(user.getPhoneNumber())
//                .serviceEnum(service.getName())
//                .build();
//    }
//
//    private void checkIfNullIsPresent(String methodName, Object... objects) {
//        List<Object> checksForNull = new ArrayList<>(Arrays.asList(objects));
//        if (checksForNull.contains(null)) {
//            throw new NullPointerException(String.format("%s null was found", methodName));
//        }
//    }
//
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
