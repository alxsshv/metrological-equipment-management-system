package main.service.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import main.dto.rest.MiStatusDto;
import main.model.MiStatus;
import main.repository.MiStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
@AllArgsConstructor
public class MiStatusAlreadyExistValidator implements ConstraintValidator<MiStatusAlreadyExist, MiStatusDto> {
    @Autowired
    private final MiStatusRepository miStatusRepository;


    @Override
    public boolean isValid(MiStatusDto miStatusDto, ConstraintValidatorContext constraintValidatorContext) {
        MiStatus miStatusFromDb = miStatusRepository.findByStatus(miStatusDto.getStatus());
        return miStatusFromDb == null;
    }

}
