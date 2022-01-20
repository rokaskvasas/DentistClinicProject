package eu.codeacademy.projecttooth.tooth.repository;

import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailabilityEntity, Long> {



    @Query("select da from DoctorAvailabilityEntity da left join DoctorEntity doc on doc.doctorId = da.doctorEntity.doctorId where doc.user.userId=?1 and da.doctorAvailabilityId =?2")
    Optional<DoctorAvailabilityEntity> findByUserAndAvailabilityId(Long userId, Long availabilityId);

    @Query("select da from DoctorAvailabilityEntity da left join DoctorEntity doc on doc.doctorId = da.doctorEntity.doctorId where doc.user.userId = ?1 ")
    Page<DoctorAvailabilityEntity> findAllByUserId(Long userId, Pageable pageable);

}
