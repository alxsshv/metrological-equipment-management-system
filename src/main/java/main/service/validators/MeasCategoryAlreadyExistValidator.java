package main.service.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import main.dto.rest.MeasCategoryDto;
import main.model.MeasCategory;
import main.repository.MeasCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
public class MeasCategoryAlreadyExistValidator implements ConstraintValidator<MeasCategoryAlreadyExist, MeasCategoryDto> {
    @Autowired
    private final MeasCategoryRepository measCategoryRepository;


    @Override
    public boolean isValid(MeasCategoryDto measCategoryDto, ConstraintValidatorContext constraintValidatorContext) {
        MeasCategory measCategoryFromDb = measCategoryRepository.findByTitle(measCategoryDto.getTitle());
        return measCategoryFromDb == null;
    }
}
