package eu.codeacademy.projecttooth.tooth.repository;

import eu.codeacademy.projecttooth.tooth.entity.DoctorServiceAvailabilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorServiceAvailabilityRepository extends JpaRepository<DoctorServiceAvailabilityEntity, Long> {


    List<DoctorServiceAvailabilityEntity> findAllByDoctorAvailabilityDoctorEntityUserUserId(Long aLong);


}
