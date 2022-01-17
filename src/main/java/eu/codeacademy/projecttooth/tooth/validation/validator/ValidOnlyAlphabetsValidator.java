package eu.codeacademy.projecttooth.tooth.validation.validator;

import eu.codeacademy.projecttooth.tooth.validation.ValidOnlyAlphabets;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Slf4j
public class ValidOnlyAlphabetsValidator implements ConstraintValidator<ValidOnlyAlphabets, String> {

    @Override
    public void initialize(ValidOnlyAlphabets constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try{
            if(!patternIsValid(value)){
                return false;
            }
        } catch (ValidationException e){
            log.error("Error at ValidOnlyAlphabetsValidator {}",e.getMessage());
        }
        return true;
    }

    private boolean patternIsValid(String value) {
        Pattern  pattern =
                Pattern.compile("^[A-Za-z]{2,30}$");
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
