package eu.codeacademy.projecttooth.tooth.validation.validator;

import eu.codeacademy.projecttooth.tooth.validation.ValidPhoneNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidPhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber,String> {
    @Override
    public void initialize(ValidPhoneNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return hasValidPhoneNumber(value);
    }

    private boolean hasValidPhoneNumber(String phoneNumber){
        return phoneNumber.length() == 12 && phoneNumber.charAt(0) == '+' && phoneNumber.startsWith("3706", 1);
    }
}
