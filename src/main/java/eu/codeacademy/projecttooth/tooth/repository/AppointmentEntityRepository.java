package eu.codeacademy.projecttooth.tooth.repository;

import eu.codeacademy.projecttooth.tooth.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentEntityRepository extends JpaRepository<AppointmentEntity, Long> {
}
