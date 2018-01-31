package me.mjaroszewicz.crmapp.validators;

import me.mjaroszewicz.crmapp.annotations.ValidComplaintDto;
import me.mjaroszewicz.crmapp.dto.ComplaintDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ComplaintDtoValidator implements ConstraintValidator<ValidComplaintDto, ComplaintDto> {


    @Override
    public void initialize(ValidComplaintDto constraintAnnotation) {

    }

    @Override
    public boolean isValid(ComplaintDto value, ConstraintValidatorContext context) {

        System.out.println("dupa");

        boolean validId = value.getOrderId() > 0;

        boolean validDeadline = value.getDeadline() != null && value.getDeadline().matches("\\d{4}.\\d{2}.\\d{2}");

        boolean validDescription = value.getDescription() != null && !value.getDescription().isEmpty();

        return validId && validDeadline && validDescription;
    }
}

