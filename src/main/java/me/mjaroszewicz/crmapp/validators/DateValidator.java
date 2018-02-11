package me.mjaroszewicz.crmapp.validators;

import me.mjaroszewicz.crmapp.annotations.ValidDate;
import me.mjaroszewicz.crmapp.annotations.ValidEmail;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class DateValidator implements ConstraintValidator<ValidDate, String>{

    private Pattern datePattern = Pattern.compile("\\d{4}.\\d{2}.\\d{2}");

    @Override
    public void initialize(ValidDate constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return datePattern.matcher(value).matches();
    }
}
