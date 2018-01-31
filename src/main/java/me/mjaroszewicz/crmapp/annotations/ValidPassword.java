package me.mjaroszewicz.crmapp.annotations;

import me.mjaroszewicz.crmapp.validators.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {

    String message() default "Invalid password.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
