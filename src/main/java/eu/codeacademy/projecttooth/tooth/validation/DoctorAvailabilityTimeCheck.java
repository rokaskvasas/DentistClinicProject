package eu.codeacademy.projecttooth.tooth.validation;

import eu.codeacademy.projecttooth.tooth.validation.validator.DoctorAvailabilityTimeCheckValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD})
@Constraint(validatedBy = DoctorAvailabilityTimeCheckValidator.class)
public @interface DoctorAvailabilityTimeCheck {
    String message() default "StartTime or endTime is incorrect";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
