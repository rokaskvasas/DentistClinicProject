package eu.codeacademy.projecttooth.tooth.repository;

import eu.codeacademy.projecttooth.tooth.entity.AppointmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

    @Query("select app from AppointmentEntity app where app.patient.user.userId = ?1")
    Optional<AppointmentEntity> findByUserId(Long userId);

    Page<AppointmentEntity> findAllByPatientUserUserId(Long userId, Pageable page);
}
