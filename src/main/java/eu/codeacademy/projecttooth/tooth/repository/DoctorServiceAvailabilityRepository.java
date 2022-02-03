package eu.codeacademy.projecttooth.tooth.repository;

import eu.codeacademy.projecttooth.tooth.entity.DoctorServiceAvailabilityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorServiceAvailabilityRepository extends JpaRepository<DoctorServiceAvailabilityEntity, Long>, JpaSpecificationExecutor<DoctorServiceAvailabilityEntity> {


    @Query("select dsa from DoctorServiceAvailabilityEntity dsa where dsa.doctorAvailability.doctorEntity.user.userId = ?1")
    Page<DoctorServiceAvailabilityEntity> findAllByUserId(Long aLong, Pageable pageable);


    @Query("SELECT dsa from DoctorServiceAvailabilityEntity dsa left join UserEntity  u on u.userId = dsa.doctorAvailability.doctorEntity.user.userId where u.userId= ?1 and dsa.doctorAvailabilityServiceId = ?2 ")
    Optional<DoctorServiceAvailabilityEntity> findByUserAndServiceAvailabilityId(Long userId, Long serviceAvailabilityId);


    @Query("SELECT service from DoctorServiceAvailabilityEntity  as service join DoctorAvailabilityEntity da on da.doctorAvailabilityId = service.doctorAvailability.doctorAvailabilityId where da.reserved = false")
    Page<DoctorServiceAvailabilityEntity> findAllAvailable(Pageable pageable);


}
