package eu.codeacademy.projecttooth.tooth.repository;

import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorAvailabilityEntityRepository extends JpaRepository<DoctorAvailabilityEntity, Long> {
}
