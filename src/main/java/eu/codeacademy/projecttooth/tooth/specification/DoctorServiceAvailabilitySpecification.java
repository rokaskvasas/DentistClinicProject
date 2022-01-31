package eu.codeacademy.projecttooth.tooth.specification;

import eu.codeacademy.projecttooth.tooth.entity.*;
import eu.codeacademy.projecttooth.tooth.model.DoctorServiceAvailabilitySearchCriteria;
import eu.codeacademy.projecttooth.tooth.model.modelenum.ServiceEnum;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.Objects;

@Component
public class DoctorServiceAvailabilitySpecification {


    private Specification<DoctorServiceAvailabilityEntity> getService(ServiceEnum serviceEnum) {
        if (serviceEnum == null) {
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            Join<DoctorServiceAvailabilityEntity, ServiceEntity> serviceEntityJoin = root.join("service");
            Predicate equalPredicate = criteriaBuilder.equal(serviceEntityJoin.get("name"), serviceEnum);
            query.distinct(true);
            return equalPredicate;
        });
    }

    private Specification<DoctorServiceAvailabilityEntity> getLocation(String locationCity) {
        if (locationCity == null) {
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            Join<DoctorServiceAvailabilityEntity, DoctorAvailabilityEntity> availabilityEntityJoin = root.join("doctorAvailability");
            Join<DoctorAvailabilityEntity, DoctorEntity> doctorEntityJoin = availabilityEntityJoin.join("doctorEntity");
            Join<DoctorEntity, LocationEntity> locationEntityJoin = doctorEntityJoin.join("location");
            Predicate likePredicate = criteriaBuilder.like(locationEntityJoin.get("city"), "%" + locationCity + "%");
            query.distinct(true);
            return likePredicate;
        });
    }

    private Specification<DoctorServiceAvailabilityEntity> getAfterStartTime(LocalDateTime startTime) {
        if (startTime == null) {
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            Join<DoctorServiceAvailabilityEntity, DoctorAvailabilityEntity> availabilityEntityJoin = root.join("doctorAvailability");
            return criteriaBuilder.greaterThan(availabilityEntityJoin.get("startTime"), startTime);
        });
    }

    private Specification<DoctorServiceAvailabilityEntity> getBeforeEndTime(LocalDateTime endTime) {
        if (endTime == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> {
            Join<DoctorServiceAvailabilityEntity, DoctorAvailabilityEntity> availabilityEntityJoin = root.join("doctorAvailability");
            return criteriaBuilder.lessThan(availabilityEntityJoin.get("endTime"), endTime);
        };
    }

    private Specification<DoctorServiceAvailabilityEntity> findByDoctorFirstName(String doctorFirstName) {
        if (Objects.isNull(doctorFirstName)) {
            return null;
        }
        return (root, query, criteriaBuilder) -> {

            Join<DoctorServiceAvailabilityEntity, DoctorAvailabilityEntity> availabilityEntityJoin = root.join("doctorAvailability");
            Join<DoctorAvailabilityEntity, DoctorEntity> doctorEntityJoin = availabilityEntityJoin.join("doctorEntity");
            Join<DoctorEntity, UserEntity> userEntityJoin = doctorEntityJoin.join("user");
            return criteriaBuilder.like(userEntityJoin.get("firstName"), "%" + doctorFirstName + "%");
        };
    }

    private Specification<DoctorServiceAvailabilityEntity> findByDoctorLastName(String doctorLastName) {
        if (Objects.isNull(doctorLastName)) {
            return null;
        }
        return (root, query, criteriaBuilder) -> {
            Join<DoctorServiceAvailabilityEntity, DoctorAvailabilityEntity> availabilityEntityJoin = root.join("doctorAvailability");
            Join<DoctorAvailabilityEntity, DoctorEntity> doctorEntityJoin = availabilityEntityJoin.join("doctorEntity");
            Join<DoctorEntity, UserEntity> userEntityJoin = doctorEntityJoin.join("user");
            return criteriaBuilder.like(userEntityJoin.get("lastName"), "%" + doctorLastName + "%");
        };
    }

    private Specification<DoctorServiceAvailabilityEntity> availableServices() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("reserved"));
    }

    public Specification<DoctorServiceAvailabilityEntity> findAllWithFilters(DoctorServiceAvailabilitySearchCriteria doctorServiceAvailabilitySearchCriteria) {
        return
                Specification.where(getService(doctorServiceAvailabilitySearchCriteria.getService()))
                        .and(getLocation(doctorServiceAvailabilitySearchCriteria.getCity()))
                        .and(getAfterStartTime(doctorServiceAvailabilitySearchCriteria.getStartTime()))
                        .and(getBeforeEndTime(doctorServiceAvailabilitySearchCriteria.getEndTime()))
                        .and(findByDoctorFirstName(doctorServiceAvailabilitySearchCriteria.getFirstName()))
                        .and(findByDoctorLastName(doctorServiceAvailabilitySearchCriteria.getLastName()))
                        .and(availableServices());

    }
}
