package me.mjaroszewicz.crmapp.annotations;

import me.mjaroszewicz.crmapp.validators.ComplaintDtoValidator;
import me.mjaroszewicz.crmapp.validators.UserRegistrationDtoValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ComplaintDtoValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidComplaintDto {

    String message() default "Provided data do not match required criteria.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
