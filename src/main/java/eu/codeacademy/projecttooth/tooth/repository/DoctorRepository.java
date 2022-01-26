package eu.codeacademy.projecttooth.tooth.repository;

import eu.codeacademy.projecttooth.tooth.entity.DoctorEntity;
import eu.codeacademy.projecttooth.tooth.model.modelenum.StatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {


    @Query("SELECT d FROM DoctorEntity as d LEFT JOIN UserEntity u ON d.user.userId = u.userId where u.userId= ?1")
    Optional<DoctorEntity> findDoctorByUserId(Long userId);

    @Modifying
    void removeByUser_UserId(Long userId);

    @Query("select doc from DoctorEntity doc where doc.status =?1")
    Page<DoctorEntity> findAllDoctorsByStatus(StatusEnum status, Pageable page);
}
