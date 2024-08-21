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
@Constraint(validatedBy = MiStandardAlreadyExistValidator.class)
@Documented
public @interface MiStandardAlreadyExist {
    String message() default "Запись об эталоне с номером уже существует";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
