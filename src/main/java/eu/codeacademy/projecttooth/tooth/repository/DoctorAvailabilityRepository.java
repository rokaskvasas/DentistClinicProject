package eu.codeacademy.projecttooth.tooth.repository;

import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailabilityEntity, Long> {


    List<DoctorAvailabilityEntity> findAllByDoctorEntityDoctorId(Long doctorId);

    List<DoctorAvailabilityEntity> findAllByDoctorEntityUserUserId(Long userId);

    @Modifying
    void removeByDoctorAvailabilityId(Long id);
}
