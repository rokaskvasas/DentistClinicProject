package eu.codeacademy.projecttooth.tooth.model.metamodel;

import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.entity.DoctorServiceAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.entity.ServiceEntity;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;
@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DoctorServiceAvailabilityEntity.class)
public class DoctorServiceAvailabilityEntity_ {

    public static volatile SingularAttribute<DoctorServiceAvailabilityEntity, Long> doctorServiceAvailabilityId;
    public static volatile SingularAttribute<DoctorServiceAvailabilityEntity, ServiceEntity> service;
    public static volatile SingularAttribute<DoctorServiceAvailabilityEntity, DoctorAvailabilityEntity> doctorAvailability;
    public static volatile SingularAttribute<DoctorServiceAvailabilityEntity, LocalDateTime> createdAt;
    public static volatile SingularAttribute<DoctorServiceAvailabilityEntity, LocalDateTime> updatedAt;

}
