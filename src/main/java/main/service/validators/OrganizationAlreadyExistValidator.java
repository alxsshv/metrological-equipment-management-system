package main.service.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import main.dto.rest.OrganizationDto;
import main.model.Organization;
import main.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
public class OrganizationAlreadyExistValidator implements ConstraintValidator<OrganizationAlreadyExist, OrganizationDto> {
    @Autowired
    private final OrganizationRepository organizationRepository;

    @Override
    public boolean isValid(OrganizationDto organizationDto, ConstraintValidatorContext constraintValidatorContext) {
        Organization organizationFromDb = organizationRepository.findByNotation(organizationDto.getNotation());
        return organizationFromDb == null;
    }

}
