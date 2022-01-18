package eu.codeacademy.projecttooth.tooth.repository;

import eu.codeacademy.projecttooth.tooth.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {


    //    left join LocationEntity as l on d.locationId = l.locationId
    @Query("SELECT d FROM DoctorEntity as d LEFT JOIN UserEntity u ON d.user.userId = u.userId where u.userId= ?1")
    Optional<DoctorEntity> findDoctor(Long aLong);

    @Modifying
    void removeByUser_UserId(Long userId);
}
