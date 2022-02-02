package eu.codeacademy.projecttooth.tooth.specification;

import eu.codeacademy.projecttooth.tooth.entity.DoctorEntity;
import eu.codeacademy.projecttooth.tooth.entity.DoctorEntity_;
import eu.codeacademy.projecttooth.tooth.entity.UserEntity;
import eu.codeacademy.projecttooth.tooth.entity.UserEntity_;;
import eu.codeacademy.projecttooth.tooth.model.modelenum.RoleEnum;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class UnverifiedSpecification {

    public Specification<DoctorEntity> findUnverifiedDoctors(){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>(){{
                Join<DoctorEntity, UserEntity> userEntityJoin = root.join(DoctorEntity_.user);
                add(criteriaBuilder.lessThan(root.get(DoctorEntity_.createdAt), LocalDateTime.now().minusHours(72)));
                add(criteriaBuilder.equal(userEntityJoin.get(UserEntity_.role), RoleEnum.ROLE_UNVERIFIED_DOCTOR.determinateRole()));
            }};
             return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Specification<UserEntity> findUnverifiedUsers(){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>(){{
                add(criteriaBuilder.lessThan(root.get(UserEntity_.createdAt), LocalDateTime.now().minusHours(72)));
                add(criteriaBuilder.equal(root.get(UserEntity_.role), RoleEnum.ROLE_UNVERIFIED_DOCTOR.determinateRole()));
            }};
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
