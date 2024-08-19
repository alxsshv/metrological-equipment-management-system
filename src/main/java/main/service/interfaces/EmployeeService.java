package main.service.interfaces;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import main.dto.rest.EmployeeDto;
import main.model.Employee;
import main.service.validators.EmployeeAlreadyExist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {
    void save(@EmployeeAlreadyExist @Valid EmployeeDto employeeDto);
    EmployeeDto findById(long id);
    Employee getEmployeeById(long id);
    Page<EmployeeDto> findBySurname(@NotBlank(message = "Поле для поиска не может быть пустым") String surname, Pageable pageable);
    List<EmployeeDto> findBySurname(@NotBlank(message = "Поле для поиска не может быть пустым") String surname);
    Page<EmployeeDto> findAll(Pageable pageable);
    List<EmployeeDto> findAll();
    void update(@Valid EmployeeDto employeeDto);
    void delete(long id);

}
