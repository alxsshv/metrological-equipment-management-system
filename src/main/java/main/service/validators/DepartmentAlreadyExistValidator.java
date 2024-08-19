package main.service.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import main.dto.rest.DepartmentDto;
import main.model.Department;
import main.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
public class DepartmentAlreadyExistValidator implements ConstraintValidator<DepartmentAlreadyExist, DepartmentDto> {
    @Autowired
    private final DepartmentRepository departmentRepository;

    @Override
    public boolean isValid(DepartmentDto departmentDto, ConstraintValidatorContext constraintValidatorContext) {
        Department departmentFromDb = departmentRepository.findByNotation(departmentDto.getNotation());
        return departmentFromDb == null;
    }

}
