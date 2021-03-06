package eu.codeacademy.projecttooth.tooth.service;

import eu.codeacademy.projecttooth.tooth.dto.ModifyAppointmentDto;
import eu.codeacademy.projecttooth.tooth.model.Appointment;
import eu.codeacademy.projecttooth.tooth.model.AppointmentSearchCriteria;
import eu.codeacademy.projecttooth.tooth.security.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface AppointmentService {
    Appointment createAppointment(Long userId, ModifyAppointmentDto payload);

    Appointment getAppointmentAsPatient(Long appointmentId, Long userId);

    Appointment updateAppointment(Long userId, ModifyAppointmentDto appointmentId);

    void deleteAppointment(Long userId, Long appointmentId);

    void deleteExpiredAppointments();

    Appointment getAppointmentAsDoctor(Long appointmentId, Long userId);

    Page<Appointment> findAllAppointments(UserPrincipal principal, AppointmentSearchCriteria searchCriteria, int pageNumber, int pageSize);

}
