package eu.codeacademy.projecttooth.tooth.validation.validator;


import eu.codeacademy.projecttooth.tooth.repository.UserRepository;
import eu.codeacademy.projecttooth.tooth.validation.ValidEmail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Slf4j
public class ValidEmailValidator implements ConstraintValidator<ValidEmail, String> {

    private ValidEmail validEmail;

    private final UserRepository userRepository;

    @Override
    public void initialize(ValidEmail constraintAnnotation) {
        this.validEmail = constraintAnnotation;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext ctx) {
        try{
            if(!patternIsValid(email)){
                return false;
            }
            if(!emailIsUnique(email)){
                return false;
            }
        } catch (ValidationException e){
            log.info("Error at ValidEmailvalidator: {}", e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private boolean patternIsValid(String email) {
        Pattern pattern =
                Pattern.compile("^[\\w]{1,}[\\w.+-]{0,}@[\\w-]{2,}([.][a-zA-Z]{2,}|[.][a-zA-Z]{2,})$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean emailIsUnique(String email) {
        return userRepository.findByEmail(email).isEmpty();
    }
}
