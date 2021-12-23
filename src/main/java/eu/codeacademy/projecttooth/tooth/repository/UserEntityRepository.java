package eu.codeacademy.projecttooth.tooth.repository;

import eu.codeacademy.projecttooth.tooth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
}
