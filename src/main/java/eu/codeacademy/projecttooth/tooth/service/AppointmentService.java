package eu.codeacademy.projecttooth.tooth.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface AppointmentService {
    void createAppointment(Long userId, DoctorServiceAvailabilityDto payload);

    Page<AppointmentDto> getAppointmentPageable(Long userId, int pageNumber, int pageSize);

    AppointmentDto getAppointment(Long userId, Long appointmentId);

    void updateAppointment(Long userId, AppointmentDto appointmentId);

    void deleteAppointment(Long userId, Long appointmentId);
}
