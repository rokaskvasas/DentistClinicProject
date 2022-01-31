package eu.codeacademy.projecttooth.tooth.validation.validator;

import eu.codeacademy.projecttooth.tooth.validation.ValidPassword;
import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                // at least 8 chars
                new LengthRule(8,30),
                // upper case
                new CharacterRule(EnglishCharacterData.UpperCase,1),
                // lower case
                new CharacterRule(EnglishCharacterData.LowerCase,1),
                // one digit
                new CharacterRule(EnglishCharacterData.Digit,1),
                // one symbol
                new CharacterRule(EnglishCharacterData.Special,1),
                // no white space
                new WhitespaceRule()
        ));
        RuleResult ruleResult = validator.validate(new PasswordData(password));
        if(ruleResult.isValid()){
            return true;
        }
        List<String> messages = validator.getMessages(ruleResult);

        String messageTemplate = String.join(",", messages);
        context.buildConstraintViolationWithTemplate(messageTemplate)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }


}
