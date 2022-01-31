package eu.codeacademy.projecttooth.tooth.validation;

import eu.codeacademy.projecttooth.tooth.validation.validator.ValidPhoneNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidPhoneNumberValidator.class)
public @interface ValidPhoneNumber {
    String message() default "Invalid phone number";

    Class<?>[] groups() default {};

    Class<? extends Payload> [] payload() default {};


}
