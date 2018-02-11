package me.mjaroszewicz.crmapp.validators;

import me.mjaroszewicz.crmapp.annotations.ValidPhone;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhone, String>{

    //9 numbers with optional international prefix
    private static final String PHONE_REGEX = "(\\+\\d{2})?\\d{9}";

    private static final Pattern phonePattern = Pattern.compile(PHONE_REGEX);

    @Override
    public void initialize(ValidPhone constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return phonePattern.matcher(value).matches();
    }
}
