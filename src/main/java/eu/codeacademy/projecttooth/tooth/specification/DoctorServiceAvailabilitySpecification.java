package eu.codeacademy.projecttooth.tooth.specification;

import eu.codeacademy.projecttooth.tooth.entity.*;
import eu.codeacademy.projecttooth.tooth.model.modelenum.ServiceEnum;
import eu.codeacademy.projecttooth.tooth.test.DSASearchCriteria;
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
            Predicate afterStartTime = criteriaBuilder.greaterThan(availabilityEntityJoin.get("startTime"), startTime);
            return afterStartTime;
        });
    }

    private Specification<DoctorServiceAvailabilityEntity> getBeforeEndTime(LocalDateTime endTime) {
        if (endTime == null) {
            return null;
        }
        return (root, query, criteriaBuilder) -> {
            Join<DoctorServiceAvailabilityEntity, DoctorAvailabilityEntity> availabilityEntityJoin = root.join("doctorAvailability");
            Predicate beforeEndTime = criteriaBuilder.lessThan(availabilityEntityJoin.get("endTime"), endTime);
            return beforeEndTime;
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
            Predicate firstName = criteriaBuilder.like(userEntityJoin.get("firstName"), "%" + doctorFirstName + "%");
            return firstName;
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
            Predicate lastName = criteriaBuilder.like(userEntityJoin.get("lastName"), "%" + doctorLastName + "%");
            return lastName;
        };
    }
    private Specification<DoctorServiceAvailabilityEntity> availableServices(){
        return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("reserved"));
    }
//    public Specification<DoctorServiceAvailabilityEntity> findDoctorByFirstOrLastName(String doctorFirstName, String doctorLastName) {
//        if (Objects.isNull(doctorFirstName) && Objects.isNull(doctorLastName)) {
//            return null;
//        }
//        return (root, query, criteriaBuilder) -> {
//            List<Predicate> predicates = new ArrayList<>();
//            Join<DoctorServiceAvailabilityEntity, DoctorAvailabilityEntity> availabilityEntityJoin = root.join("doctorAvailability");
//            Join<DoctorAvailabilityEntity, DoctorEntity> doctorEntityJoin = availabilityEntityJoin.join("doctorEntity");
//            Join<DoctorEntity, UserEntity> userEntityJoin = doctorEntityJoin.join("user");
//            predicates.add(criteriaBuilder.like(userEntityJoin.get("firstName"), "%" + doctorFirstName + "%"));
//            predicates.add(criteriaBuilder.like(userEntityJoin.get("lastName"), "%" + doctorLastName + "%"));
//            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
//        };
//    }


    public Specification<DoctorServiceAvailabilityEntity> findAllWithFilters(String city, ServiceEnum serviceName, LocalDateTime startTime, LocalDateTime endTime, String doctorFirstName, String doctorLastName) {
        return
                Specification.where(getService(serviceName))
                        .and(getLocation(city))
                        .and(getAfterStartTime(startTime))
                        .and(getBeforeEndTime(endTime))
//                        .and(findDoctorByFirstOrLastName(doctorFirstName,doctorLastName));
                        .and(findByDoctorFirstName(doctorFirstName))
                        .and(findByDoctorLastName(doctorLastName))
                        .and(availableServices());

    }
    public Specification<DoctorServiceAvailabilityEntity> findAllWithFiltersTEST(DSASearchCriteria criteria) {
        return
                Specification.where(getService(criteria.getService()))
                        .and(getLocation(criteria.getCity()))
                        .and(getAfterStartTime(criteria.getStartTime()))
                        .and(getBeforeEndTime(criteria.getEndTime()))
                        .and(findByDoctorFirstName(criteria.getFirstName()))
                        .and(findByDoctorLastName(criteria.getLastName()))
                        .and(availableServices());

    }
}
