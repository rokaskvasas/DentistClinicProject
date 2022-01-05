package eu.codeacademy.projecttooth.tooth.repository;

import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailabilityEntity, Long> {


    Optional<DoctorAvailabilityEntity> findByDoctorEntityUserUserId(Long userId);

    List<DoctorAvailabilityEntity> findAllByDoctorEntityUserUserId(Long userId);

    @Modifying
    void removeByDoctorAvailabilityId(Long id);
}
