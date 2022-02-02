package eu.codeacademy.projecttooth.tooth.model.metamodel;

import eu.codeacademy.projecttooth.tooth.entity.AppointmentEntity;
import eu.codeacademy.projecttooth.tooth.entity.DoctorServiceAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.entity.PatientEntity;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AppointmentEntity.class)
public class AppointmentEntity_ {
    public static volatile SingularAttribute<AppointmentEntity, Long> appointmentId;
    public static volatile SingularAttribute<AppointmentEntity, LocalDateTime> startTime;
    public static volatile SingularAttribute<AppointmentEntity, LocalDateTime> endTime;
    public static volatile SingularAttribute<AppointmentEntity, DoctorServiceAvailabilityEntity> doctorServiceAvailability;
    public static volatile SingularAttribute<AppointmentEntity, PatientEntity> patient;
    public static volatile SingularAttribute<AppointmentEntity, LocalDateTime> createdAt;
    public static volatile SingularAttribute<AppointmentEntity, LocalDateTime> updatedAt;
}
