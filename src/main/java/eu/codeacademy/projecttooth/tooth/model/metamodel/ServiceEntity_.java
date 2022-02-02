package eu.codeacademy.projecttooth.tooth.model.metamodel;

import eu.codeacademy.projecttooth.tooth.entity.ServiceEntity;
import eu.codeacademy.projecttooth.tooth.model.modelenum.QualificationEnum;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ServiceEntity.class)
public class ServiceEntity_ {
    public static volatile SingularAttribute<ServiceEntity,Long> serviceId;
    public static volatile SingularAttribute<ServiceEntity,String> name;
    public static volatile SingularAttribute<ServiceEntity, QualificationEnum> minimumQualification;
}
