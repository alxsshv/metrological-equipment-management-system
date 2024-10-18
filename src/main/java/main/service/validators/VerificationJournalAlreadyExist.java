package main.service.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;

@Target({PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = VerificationJournalAlreadyExistValidator.class)
@Documented
public @interface VerificationJournalAlreadyExist {
    String message() default "Журнал с указанным номером уже существует";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };


}
