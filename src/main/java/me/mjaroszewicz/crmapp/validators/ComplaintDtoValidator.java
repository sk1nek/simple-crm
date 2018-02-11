package me.mjaroszewicz.crmapp.validators;

import me.mjaroszewicz.crmapp.annotations.ValidComplaintDto;
import me.mjaroszewicz.crmapp.dto.ComplaintDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class ComplaintDtoValidator implements ConstraintValidator<ValidComplaintDto, ComplaintDto> {

    private Pattern datePattern = Pattern.compile("\\d{4}.\\d{2}.\\d{2}");

    @Override
    public void initialize(ValidComplaintDto constraintAnnotation) {

    }

    @Override
    public boolean isValid(ComplaintDto value, ConstraintValidatorContext context) {

        boolean validId = value.getOrderId() != null && value.getOrderId().longValue() > 0;

        boolean validDeadline = value.getDeadline() != null && datePattern.matcher(value.getDeadline()).matches();

        boolean validDescription = value.getDescription() != null && !value.getDescription().isEmpty();

        return validId && validDeadline && validDescription;
    }
}

