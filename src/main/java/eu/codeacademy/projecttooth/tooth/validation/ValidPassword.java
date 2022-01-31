package eu.codeacademy.projecttooth.tooth.validation;

import eu.codeacademy.projecttooth.tooth.validation.validator.ValidPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.ANNOTATION_TYPE,ElementType.METHOD})
@Constraint(validatedBy = ValidPasswordValidator.class)
public @interface ValidPassword {
    String message() default "Invalid symbols";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
