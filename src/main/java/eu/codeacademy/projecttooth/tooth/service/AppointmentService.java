package eu.codeacademy.projecttooth.tooth.service;

import eu.codeacademy.projecttooth.tooth.dto.AppointmentDto;
import eu.codeacademy.projecttooth.tooth.dto.DoctorServiceAvailabilityDto;
import eu.codeacademy.projecttooth.tooth.model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AppointmentService {
    void createAppointment(Long userId, DoctorServiceAvailabilityDto payload);

    Page<AppointmentDto> getAppointmentPageable(Long userId, int pageNumber, int pageSize);

    AppointmentDto getAppointment(Long userId, Long appointmentId);

    void updateAppointment(Long userId, AppointmentDto appointmentId);

    void deleteAppointment(Long userId, Long appointmentId);
}
