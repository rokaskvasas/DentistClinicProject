package eu.codeacademy.projecttooth.tooth.specification;

import eu.codeacademy.projecttooth.tooth.entity.*;
import eu.codeacademy.projecttooth.tooth.model.DoctorServiceAvailabilitySearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class DoctorServiceAvailabilitySpecification {


    private Specification<DoctorServiceAvailabilityEntity> filter(DoctorServiceAvailabilitySearchCriteria searchCriteria) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<DoctorServiceAvailabilityEntity, ServiceEntity> serviceEntityJoin = root.join(DoctorServiceAvailabilityEntity_.service);
            Join<DoctorServiceAvailabilityEntity, DoctorAvailabilityEntity> doctorAvailabilityEntityJoin = root.join(DoctorServiceAvailabilityEntity_.doctorAvailability);
            Join<DoctorAvailabilityEntity, DoctorEntity> doctorEntityJoin = doctorAvailabilityEntityJoin.join(DoctorAvailabilityEntity_.doctorEntity);
            Join<DoctorEntity, LocationEntity> locationEntityJoin = doctorEntityJoin.join(DoctorEntity_.location);
            Join<DoctorEntity, UserEntity> userEntityJoin = doctorEntityJoin.join(DoctorEntity_.user);

            predicates.add(criteriaBuilder.isFalse(doctorAvailabilityEntityJoin.get(DoctorAvailabilityEntity_.reserved)));

            if (Objects.nonNull(searchCriteria.getService())) {
                predicates.add(criteriaBuilder.equal(serviceEntityJoin.get(ServiceEntity_.name), searchCriteria.getService()));
            }

            if (Objects.nonNull(searchCriteria.getCity())) {
                predicates.add(criteriaBuilder.like(locationEntityJoin.get(LocationEntity_.city), "%" + searchCriteria.getCity() + "%"));
            }

            if (Objects.nonNull(searchCriteria.getFirstName())) {
                predicates.add(criteriaBuilder.like(userEntityJoin.get(UserEntity_.firstName), "%" + searchCriteria.getFirstName() + "%"));
            }
            if (Objects.nonNull(searchCriteria.getLastName())) {
                predicates.add(criteriaBuilder.like(userEntityJoin.get(UserEntity_.lastName), "%" + searchCriteria.getLastName() + "%"));
            }

            if (Objects.nonNull(searchCriteria.getStartTime())) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(doctorAvailabilityEntityJoin.get(DoctorAvailabilityEntity_.startTime), searchCriteria.getStartTime()));
            }
            if (Objects.nonNull(searchCriteria.getEndTime())) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(doctorAvailabilityEntityJoin.get(DoctorAvailabilityEntity_.endTime), searchCriteria.getEndTime()));
            }


            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }


    public Specification<DoctorServiceAvailabilityEntity> findAllWithFilters(DoctorServiceAvailabilitySearchCriteria doctorServiceAvailabilitySearchCriteria) {
        return
                Specification.where(filter(doctorServiceAvailabilitySearchCriteria));

    }
}
