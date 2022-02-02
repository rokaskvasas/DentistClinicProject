package eu.codeacademy.projecttooth.tooth.model.metamodel;

import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.entity.DoctorEntity;
import eu.codeacademy.projecttooth.tooth.entity.DoctorServiceAvailabilityEntity;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;
@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DoctorAvailabilityEntity.class)
public class DoctorAvailabilityEntity_ {
    public static volatile SingularAttribute<DoctorAvailabilityEntity,Long> doctorAvailabilityId;
    public static volatile SingularAttribute<DoctorAvailabilityEntity, LocalDateTime> startTime;
    public static volatile SingularAttribute<DoctorAvailabilityEntity, LocalDateTime> endTime;
    public static volatile SingularAttribute<DoctorAvailabilityEntity, DoctorEntity> doctor;
    public static volatile SetAttribute<DoctorAvailabilityEntity, DoctorServiceAvailabilityEntity> doctorServiceAvailabilities;
    public static volatile SetAttribute<DoctorAvailabilityEntity, LocalDateTime> createdAt;
    public static volatile SetAttribute<DoctorAvailabilityEntity, LocalDateTime> updatedAt;
}
