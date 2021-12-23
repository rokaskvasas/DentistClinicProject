package eu.codeacademy.projecttooth.tooth.repository;

import eu.codeacademy.projecttooth.tooth.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientEntityRepository extends JpaRepository<PatientEntity, Long> {
}
