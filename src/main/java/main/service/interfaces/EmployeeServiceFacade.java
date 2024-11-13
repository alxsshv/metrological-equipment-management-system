package main.service.interfaces;

import jakarta.validation.Valid;
import main.dto.rest.EmployeeDto;
import main.model.Employee;
import main.service.validators.EmployeeAlreadyExist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeServiceFacade {
    void save(@EmployeeAlreadyExist @Valid EmployeeDto employeeDto);
    EmployeeDto findById(long id);
    Employee getEmployeeById(long id);
    Page<EmployeeDto> findBySurname(String surname, Pageable pageable);
    Page<EmployeeDto> findAll(Pageable pageable);
    List<EmployeeDto> findAll();
    void update(@Valid EmployeeDto employeeDto);
    void delete(long id);
}
