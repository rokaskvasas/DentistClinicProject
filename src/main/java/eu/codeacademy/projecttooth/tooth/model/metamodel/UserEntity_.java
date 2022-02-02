package eu.codeacademy.projecttooth.tooth.model.metamodel;

import eu.codeacademy.projecttooth.tooth.entity.UserEntity;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;
@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserEntity.class)
public class UserEntity_ {
    public static volatile SingularAttribute<UserEntity, Long> userId;
    public static volatile SingularAttribute<UserEntity, String> firstName;
    public static volatile SingularAttribute<UserEntity, String> lastName;
    public static volatile SingularAttribute<UserEntity, String> email;
    public static volatile SingularAttribute<UserEntity, String> password;
    public static volatile SingularAttribute<UserEntity, String> phoneNumber;
    public static volatile SingularAttribute<UserEntity, String> role;
    public static volatile SingularAttribute<UserEntity, LocalDateTime> createdAt;
}
