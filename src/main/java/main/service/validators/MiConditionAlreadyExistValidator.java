package main.service.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import main.dto.rest.MiConditionDto;
import main.model.MiCondition;
import main.repository.MiConditionRepository;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
public class MiConditionAlreadyExistValidator implements ConstraintValidator<MiConditionAlreadyExist, MiConditionDto> {
    @Autowired
    private final MiConditionRepository miConditionRepository;


    @Override
    public boolean isValid(MiConditionDto miConditionDto, ConstraintValidatorContext constraintValidatorContext) {
        MiCondition miConditionFromDb = miConditionRepository.findByTitle(miConditionDto.getTitle());
        return miConditionFromDb == null;
    }

}
