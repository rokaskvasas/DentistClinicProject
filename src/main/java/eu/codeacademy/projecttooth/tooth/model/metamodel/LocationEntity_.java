package eu.codeacademy.projecttooth.tooth.model.metamodel;

import eu.codeacademy.projecttooth.tooth.entity.LocationEntity;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(LocationEntity.class)
public class LocationEntity_ {

    public static volatile SingularAttribute<LocationEntity, Long> locationId;
    public static volatile SingularAttribute<LocationEntity, String> name;
    public static volatile SingularAttribute<LocationEntity, String> city;
}
