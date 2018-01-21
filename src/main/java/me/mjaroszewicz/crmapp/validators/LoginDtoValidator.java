package me.mjaroszewicz.crmapp.validators;

import me.mjaroszewicz.crmapp.annotations.ValidLoginDto;
import me.mjaroszewicz.crmapp.dto.LoginDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LoginDtoValidator implements ConstraintValidator<ValidLoginDto, LoginDto> {

    @Override
    public void initialize(ValidLoginDto constraintAnnotation) {

    }

    @Override
    public boolean isValid(LoginDto value, ConstraintValidatorContext context) {

        boolean validUsername = !value.getUsername().isEmpty();

        boolean validPassword = !value.getPassword().isEmpty();

        return validUsername && validPassword;
    }
}
