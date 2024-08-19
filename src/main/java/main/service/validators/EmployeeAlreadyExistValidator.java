package main.service.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import main.dto.rest.EmployeeDto;
import main.model.Employee;
import main.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
public class EmployeeAlreadyExistValidator implements ConstraintValidator<EmployeeAlreadyExist, EmployeeDto> {
    @Autowired
    private final EmployeeRepository employeeRepository;

    @Override
    public boolean isValid(EmployeeDto employeeDto, ConstraintValidatorContext constraintValidatorContext) {
        Employee employeeFromDb = employeeRepository.findBySnils(employeeDto.getSnils());
        return employeeFromDb == null;
    }

}
