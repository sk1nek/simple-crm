package me.mjaroszewicz.crmapp.annotations;

import me.mjaroszewicz.crmapp.validators.ComplaintDtoValidator;
import me.mjaroszewicz.crmapp.validators.OrderDtoValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = OrderDtoValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidOrderDto {

    String message() default "Provided data do not match required criteria.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
