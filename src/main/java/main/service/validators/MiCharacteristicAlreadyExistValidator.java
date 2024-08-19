package main.service.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import main.dto.rest.MiCharacteristicDto;
import main.model.MiCharacteristic;
import main.model.MiDetails;
import main.repository.MiCharacteristicRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
public class MiCharacteristicAlreadyExistValidator implements ConstraintValidator<MiCharacteristicAlreadyExist, MiCharacteristicDto> {
    @Autowired
    private final MiCharacteristicRepository miCharacteristicRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public boolean isValid(MiCharacteristicDto miCharacteristicDto, ConstraintValidatorContext constraintValidatorContext) {
        MiCharacteristic miCharacteristicFromDb = miCharacteristicRepository.findByTitleAndMiDetails(miCharacteristicDto.getTitle(), modelMapper.map(miCharacteristicDto.getMiDetailsDto(), MiDetails.class));
        return miCharacteristicFromDb == null;
    }

}
