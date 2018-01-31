package me.mjaroszewicz.crmapp.validators;

import me.mjaroszewicz.crmapp.annotations.ValidEmail;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidEmail, String>{

    //RFC 5322
    private final static String PASSWORD_REGEX = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9\\s]).{6,}";
    @Override
    public void initialize(ValidEmail constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value.matches(PASSWORD_REGEX);
    }
}
