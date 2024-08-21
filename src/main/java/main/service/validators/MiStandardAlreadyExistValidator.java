package main.service.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import main.dto.rest.MiStandardDto;
import main.model.MiStandard;
import main.repository.MiStandardRepository;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
public class MiStandardAlreadyExistValidator implements ConstraintValidator<MiStandardAlreadyExist, MiStandardDto> {
    @Autowired
    private final MiStandardRepository miStandardRepository;

    @Override
    public boolean isValid(MiStandardDto miStandardDto, ConstraintValidatorContext constraintValidatorContext) {
        MiStandard miStandardFromDb = miStandardRepository.findByArshinNumber(miStandardDto.getArshinNumber());
        return miStandardFromDb == null;
    }

}
