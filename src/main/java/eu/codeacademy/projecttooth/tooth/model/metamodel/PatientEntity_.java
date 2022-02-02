package eu.codeacademy.projecttooth.tooth.model.metamodel;

import eu.codeacademy.projecttooth.tooth.entity.AppointmentEntity;
import eu.codeacademy.projecttooth.tooth.entity.PatientEntity;
import eu.codeacademy.projecttooth.tooth.entity.UserEntity;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PatientEntity.class)
public class PatientEntity_ {
    public static volatile SingularAttribute<PatientEntity, Long> patientId;
    public static volatile SingularAttribute<PatientEntity, UserEntity> user;
    public static volatile ListAttribute<PatientEntity, AppointmentEntity> appointments;
}
