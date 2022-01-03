package eu.codeacademy.projecttooth.tooth.repository;

import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorAvailabilityEntityRepository extends JpaRepository<DoctorAvailabilityEntity, Long> {


    List<DoctorAvailabilityEntity> findAllByDoctorEntityDoctorId(Long doctorId);
    List<DoctorAvailabilityEntity> findAllByDoctorEntityUserUserId(Long userId);
}
