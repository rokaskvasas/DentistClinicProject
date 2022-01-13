package eu.codeacademy.projecttooth.tooth.validation.validator;

import eu.codeacademy.projecttooth.tooth.validation.DoctorAvailabilityTimeCheck;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class DoctorAvailabilityTimeCheckValidator implements ConstraintValidator<DoctorAvailabilityTimeCheck,String > {

    private DoctorAvailabilityTimeCheck doctorAvailabilityTimeCheck;

    @Override
    public void initialize(DoctorAvailabilityTimeCheck constraintAnnotation){
        this.doctorAvailabilityTimeCheck = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return false;
    }
}
