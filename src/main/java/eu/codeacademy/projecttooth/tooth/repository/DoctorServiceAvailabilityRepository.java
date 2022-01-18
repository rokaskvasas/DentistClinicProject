package eu.codeacademy.projecttooth.tooth.repository;

import eu.codeacademy.projecttooth.tooth.entity.DoctorServiceAvailabilityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorServiceAvailabilityRepository extends JpaRepository<DoctorServiceAvailabilityEntity, Long> {


    Page<DoctorServiceAvailabilityEntity> findAllByDoctorAvailabilityDoctorEntityUserUserId(Long aLong, Pageable pageable);
    List<DoctorServiceAvailabilityEntity> findAllByDoctorAvailabilityDoctorEntityUserUserId(Long aLong);

    @Query("SELECT service from DoctorServiceAvailabilityEntity  as service where service.reserved = false")
    Page<DoctorServiceAvailabilityEntity> findAllAvailable(Pageable pageable);


}
