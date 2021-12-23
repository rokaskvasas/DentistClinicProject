package eu.codeacademy.projecttooth.tooth.repository;

import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorAvailabilityServiceEntityRepository extends JpaRepository<DoctorAvailabilityServiceEntity, Long> {
}
