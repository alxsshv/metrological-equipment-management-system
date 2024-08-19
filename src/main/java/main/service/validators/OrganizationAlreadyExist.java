package main.service.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ PARAMETER })
@Retention(RUNTIME)
@Constraint(validatedBy = OrganizationAlreadyExistValidator.class)
@Documented
public @interface OrganizationAlreadyExist {
    String message() default "Запись об организации уже существует";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
