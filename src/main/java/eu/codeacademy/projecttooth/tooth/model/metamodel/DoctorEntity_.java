package eu.codeacademy.projecttooth.tooth.model.metamodel;

import eu.codeacademy.projecttooth.tooth.entity.DoctorAvailabilityEntity;
import eu.codeacademy.projecttooth.tooth.entity.DoctorEntity;
import eu.codeacademy.projecttooth.tooth.entity.LocationEntity;
import eu.codeacademy.projecttooth.tooth.entity.UserEntity;
import eu.codeacademy.projecttooth.tooth.model.modelenum.QualificationEnum;
import eu.codeacademy.projecttooth.tooth.model.modelenum.StatusEnum;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(DoctorEntity.class)
public class DoctorEntity_ {
    public static volatile SingularAttribute<DoctorEntity, Long> doctorId;
    public static volatile SingularAttribute<DoctorEntity, String> doctorLicense;
    public static volatile SingularAttribute<DoctorEntity, StatusEnum> status;
    public static volatile SingularAttribute<DoctorEntity, QualificationEnum> qualification;
    public static volatile SingularAttribute<DoctorEntity, Long> locationId;
    public static volatile SingularAttribute<DoctorEntity, UserEntity> user;
    public static volatile SingularAttribute<DoctorEntity, LocationEntity> location;
    public static volatile ListAttribute<DoctorEntity, DoctorAvailabilityEntity> doctorAvailabilities;
    public static volatile SingularAttribute<DoctorEntity, LocalDateTime> createdAt;
    public static volatile SingularAttribute<DoctorEntity, LocalDateTime> updatedAt;
}
