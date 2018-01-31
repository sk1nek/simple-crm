package me.mjaroszewicz.crmapp.annotations;

import me.mjaroszewicz.crmapp.validators.PhoneNumberValidator;
import me.mjaroszewicz.crmapp.validators.UserRegistrationDtoValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPhone {

    String message() default "Invalid phone number.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
