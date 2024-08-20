package main.service.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import main.dto.rest.MiTypeDetailsDto;
import main.model.MiType;
import main.repository.MiTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
public class MiTypeAlreadyExistValidator implements ConstraintValidator<MiTypeAlreadyExist, MiTypeDetailsDto> {
    @Autowired
    private final MiTypeRepository miTypeRepository;

    @Override
    public boolean isValid(MiTypeDetailsDto miTypeDetailsDto, ConstraintValidatorContext constraintValidatorContext) {
        MiType miType = miTypeRepository.findByNumber(miTypeDetailsDto.getMiType().getNumber());
        return miType == null;
    }

}
