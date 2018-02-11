package me.mjaroszewicz.crmapp.validators;

import me.mjaroszewicz.crmapp.annotations.ValidComplaintDto;
import me.mjaroszewicz.crmapp.annotations.ValidOrderDto;
import me.mjaroszewicz.crmapp.dto.ComplaintDto;
import me.mjaroszewicz.crmapp.dto.OrderDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class OrderDtoValidator implements ConstraintValidator<ValidOrderDto, OrderDto> {

    private Pattern datePattern = Pattern.compile("\\d{4}.\\d{2}.\\d{2}");

    @Override
    public void initialize(ValidOrderDto constraintAnnotation) {

    }

    @Override
    public boolean isValid(OrderDto value, ConstraintValidatorContext context) {

        boolean validDate = datePattern.matcher(value.getDateDeadline()).matches();

        boolean validDescription = value.getDescription() != null && !value.getDescription().isEmpty();

        boolean validOrderName = value.getOrderName() != null && value.getOrderName().length() > 3;


        return validDate && validOrderName && validDescription;
    }
}

