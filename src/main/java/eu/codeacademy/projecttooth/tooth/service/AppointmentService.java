package eu.codeacademy.projecttooth.tooth.service;

import eu.codeacademy.projecttooth.tooth.dto.AppointmentDto;
import eu.codeacademy.projecttooth.tooth.dto.DoctorServiceAvailabilityDto;
import eu.codeacademy.projecttooth.tooth.model.Appointment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AppointmentService {
    void createAppointment(Long userId, DoctorServiceAvailabilityDto payload);

    List<AppointmentDto> getAppointmentList(Long userId);
}
