package me.mjaroszewicz.crmapp.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = me.mjaroszewicz.crmapp.validators.EmailValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmail {

    String message() default "Invalid email.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
