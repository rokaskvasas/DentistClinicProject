package eu.codeacademy.projecttooth.tooth.specification;

import eu.codeacademy.projecttooth.tooth.entity.UserEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserSpecification {

    public Specification<UserEntity> findUnverifiedUsers(){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>(){{
                add(criteriaBuilder.lessThan(root.get("createdAt"), LocalDateTime.now().minusHours(72)));
                add(criteriaBuilder.equal(root.get("role"),"ROLE_UNVERIFIED_DOCTOR"));
            }};
             return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
