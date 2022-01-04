package eu.codeacademy.projecttooth.tooth.repository;

import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorAvailabilityServiceRepository extends JpaRepository<DoctorAvailabilityServiceEntity, Long> {


    List<DoctorAvailabilityServiceEntity> findAllByDoctorAvailabilityEntity_DoctorEntity_User_UserId(Long aLong);

    @Modifying
    void removeByDoctorAvailabilityServiceId(Long serviceId);

}
