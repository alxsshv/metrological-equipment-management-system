package main.service.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import main.dto.rest.MiDetailsDto;
import main.model.MeasurementInstrument;
import main.repository.MeasurementInstrumentRepository;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
public class MiAlreadyExistValidator implements ConstraintValidator<MiAlreadyExist, MiDetailsDto> {
    @Autowired
    private final MeasurementInstrumentRepository measurementInstrumentRepository;

    @Override
    public boolean isValid(MiDetailsDto miDetailsDto, ConstraintValidatorContext constraintValidatorContext) {
        MeasurementInstrument measurementInstrumentFromDb = measurementInstrumentRepository
                .findByModificationAndSerialNum(miDetailsDto.getMiFullDto().getModification(), miDetailsDto.getMiFullDto().getSerialNum());
        return measurementInstrumentFromDb == null;
    }

}
