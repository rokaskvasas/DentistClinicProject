package eu.codeacademy.projecttooth.tooth.validation;

import eu.codeacademy.projecttooth.tooth.validation.validator.ValidOnlyAlphabetsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE,ElementType.FIELD,ElementType.METHOD})
@Constraint(validatedBy = ValidOnlyAlphabetsValidator.class)
public @interface ValidOnlyAlphabets {
    String message() default "Please use only alphabets";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
