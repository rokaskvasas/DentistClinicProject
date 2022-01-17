package eu.codeacademy.projecttooth.tooth.service;

import eu.codeacademy.projecttooth.tooth.dto.ModifyAppointmentDto;
import eu.codeacademy.projecttooth.tooth.model.Appointment;
import eu.codeacademy.projecttooth.tooth.model.DoctorServiceAvailability;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AppointmentService {
    Appointment createAppointment(Long userId, ModifyAppointmentDto payload);

    Page<Appointment> getAppointmentPageable(Long userId, int pageNumber, int pageSize);

    Appointment getAppointment(Long userId, Long appointmentId);

    Appointment updateAppointment(Long userId, ModifyAppointmentDto appointmentId);

    void deleteAppointment(Long userId, Long appointmentId);

    List<Appointment> getAppointmentList();

    void deleteExpiredAppointments();
}
