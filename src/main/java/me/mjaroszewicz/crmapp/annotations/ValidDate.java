package me.mjaroszewicz.crmapp.annotations;

import me.mjaroszewicz.crmapp.validators.DateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDate {

    String message() default "Invalid date, please use ISO format.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
