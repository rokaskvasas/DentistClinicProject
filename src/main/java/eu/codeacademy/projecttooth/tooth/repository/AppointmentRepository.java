package eu.codeacademy.projecttooth.tooth.repository;

import eu.codeacademy.projecttooth.tooth.entity.AppointmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

    @Query("select app from AppointmentEntity app where app.appointmentId = ?1 and app.patient.user.userId= ?2")
    Optional<AppointmentEntity> findByAppointmentIdAndUserIdAsPatient(Long appointmentId, Long userId);

    @Query("select app from AppointmentEntity app where app.patient.user.userId =?1")
    Page<AppointmentEntity> findAllByPatientUserId(Long userId, Pageable page);

    @Query("select app from AppointmentEntity app where app.appointmentId = ?1 and app.doctorServiceAvailability.doctorAvailability.doctorEntity.user.userId = ?2")
    Optional<AppointmentEntity> findByAppointmentIdAndUserIdAsDoctor(Long appointmentId, Long userId);

    @Query("select app from AppointmentEntity app where app.doctorServiceAvailability.doctorAvailability.doctorEntity.user.userId =?1")
    Page<AppointmentEntity> findAllByDoctorUserId(Long userId, Pageable page);
}
