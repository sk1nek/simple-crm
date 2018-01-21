package me.mjaroszewicz.crmapp.validators;

import me.mjaroszewicz.crmapp.annotations.ValidUserRegistration;
import me.mjaroszewicz.crmapp.dto.UserRegistrationDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserRegistrationDtoValidator implements ConstraintValidator<ValidUserRegistration, UserRegistrationDto> {

    //RFC 5322
    private final static String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    @Override
    public void initialize(ValidUserRegistration constraintAnnotation) {

    }

    @Override
    public boolean isValid(UserRegistrationDto value, ConstraintValidatorContext context) {

        if(!value.getUsername().matches("^[A-Za-z0-9]+(?:[_-][A-Za-z0-9]+)*$"))
            return false;

        //at least one character, one digit, one character that does not belong in a-zA-Z0-9 and at least 6 characters
        if(!value.getPassword().matches("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[^a-zA-Z0-9\\s]).{6,}"))
            return false;

        if(!value.getEmail().matches(EMAIL_REGEX))
            return false;

        return true;
    }
}
