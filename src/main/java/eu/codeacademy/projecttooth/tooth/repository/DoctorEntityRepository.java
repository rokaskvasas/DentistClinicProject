package eu.codeacademy.projecttooth.tooth.repository;

import eu.codeacademy.projecttooth.tooth.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorEntityRepository extends JpaRepository<DoctorEntity, Long> {


    Optional<DoctorEntity> findDoctorEntityByUserUserId(Long aLong);
}
