package eu.codeacademy.projecttooth.tooth.repository;

import eu.codeacademy.projecttooth.tooth.entity.DoctorServiceAvailabilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorServiceAvailabilityRepository extends JpaRepository<DoctorServiceAvailabilityEntity, Long> {


//    List<DoctorServiceAvailabilityEntity> findAllByDoctorAvailabilityEntity_DoctorEntity_User_UserId(Long aLong);
    List<DoctorServiceAvailabilityEntity> findAllByDoctorAvailabilityEntityDoctorEntityUserUserId(Long aLong);

    @Modifying
    void removeByDoctorAvailabilityServiceId(Long serviceId);

}
