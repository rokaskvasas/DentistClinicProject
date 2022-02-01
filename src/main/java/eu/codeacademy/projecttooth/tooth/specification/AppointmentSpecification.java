package eu.codeacademy.projecttooth.tooth.specification;

import eu.codeacademy.projecttooth.tooth.entity.*;
import eu.codeacademy.projecttooth.tooth.model.AppointmentSearchCriteria;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class AppointmentSpecification {

    private final CriteriaBuilder criteriaBuilder;

    public AppointmentSpecification(EntityManager entityManager) {
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }


    private Specification<AppointmentEntity> filter(AppointmentSearchCriteria searchCriteria) {
        return (root, query, criteriaBuilder1) -> {

            List<Predicate> predicates = new ArrayList<>();
            Join<AppointmentEntity, DoctorServiceAvailabilityEntity> doctorServiceAvailabilityEntityJoin = root.join("doctorServiceAvailability");
            Join<DoctorServiceAvailabilityEntity, ServiceEntity> serviceEntityJoin = doctorServiceAvailabilityEntityJoin.join("service");
            Join<DoctorServiceAvailabilityEntity, DoctorAvailabilityEntity> doctorAvailabilityEntityJoin = doctorServiceAvailabilityEntityJoin.join("doctorAvailability");
            Join<DoctorAvailabilityEntity, DoctorEntity> doctorEntityJoin = doctorAvailabilityEntityJoin.join("doctorEntity");
            Join<DoctorEntity, UserEntity> docUserEntityJoin = doctorEntityJoin.join("user");
            Join<DoctorEntity, LocationEntity> locationEntityJoin = doctorEntityJoin.join("location");

            if (Objects.nonNull(searchCriteria.getService())) {
                predicates.add(criteriaBuilder.equal(serviceEntityJoin.get("name"), searchCriteria.getService()));
            }
            if (Objects.nonNull(searchCriteria.getCity())) {
                predicates.add(criteriaBuilder.like(locationEntityJoin.get("city"), "%" + searchCriteria.getCity() + "%"));
            }
            if (Objects.nonNull(searchCriteria.getStartTime())) {
                predicates.add(criteriaBuilder.greaterThan(root.get("startTime"), searchCriteria.getStartTime()));
            }
            if (Objects.nonNull(searchCriteria.getEndTime())) {
                predicates.add(criteriaBuilder.lessThan(root.get("endTime"), searchCriteria.getEndTime()));
            }
            if(Objects.nonNull(searchCriteria.getFirstName())){
                predicates.add(criteriaBuilder.like(docUserEntityJoin.get("firstName"),searchCriteria.getFirstName()));
            }
            if(Objects.nonNull(searchCriteria.getLastName())){
                predicates.add(criteriaBuilder.like(docUserEntityJoin.get("lastName"),searchCriteria.getLastName()));
            }


            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private Specification<AppointmentEntity> setFilterForPatient(Long userId){
        return (root, query, criteriaBuilder1) -> {
            Join<AppointmentEntity,PatientEntity> patientEntityJoin = root.join("patient");
            Join<PatientEntity, UserEntity> userEntityJoin = patientEntityJoin.join("user");
            return criteriaBuilder.equal(userEntityJoin.get("userId"),userId);
        };
    }

    private Specification<AppointmentEntity> setFilterForDoctor(Long userId){
        return (root, query, criteriaBuilder1) -> {
            Join<AppointmentEntity, DoctorServiceAvailabilityEntity> doctorServiceAvailabilityEntityJoin = root.join("doctorServiceAvailability");
            Join<DoctorServiceAvailabilityEntity, DoctorAvailabilityEntity> doctorAvailabilityEntityJoin = doctorServiceAvailabilityEntityJoin.join("doctorAvailability");
            Join<DoctorAvailabilityEntity, DoctorEntity> doctorEntityJoin = doctorAvailabilityEntityJoin.join("doctorEntity");
            Join<DoctorEntity, UserEntity> docUserEntityJoin = doctorEntityJoin.join("user");
            return criteriaBuilder.equal(docUserEntityJoin.get("userId"),userId);
        };
    }


    public Specification<AppointmentEntity> findAllWithFiltersForPatient(AppointmentSearchCriteria searchCriteria, Long userId) {
        return Specification.where(filter(searchCriteria)).and(setFilterForPatient(userId));
    }

    public Specification<AppointmentEntity> findAllWithFiltersForDoctor(AppointmentSearchCriteria searchCriteria, Long userId){
        return Specification.where(filter(searchCriteria)).and(setFilterForDoctor(userId));
    }
}
