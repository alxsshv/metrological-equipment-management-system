package main.service.interfaces;

import main.dto.rest.EmployeeDto;
import main.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {
    void save(EmployeeDto employeeDto);
    EmployeeDto findById(long id);
    Employee getEmployeeById(long id);
    Page<EmployeeDto> findBySurname(String surname, Pageable pageable);
    List<EmployeeDto> findBySurname(String surname);
    Page<EmployeeDto> findAll(Pageable pageable);
    List<EmployeeDto> findAll();
    void update(EmployeeDto employeeDto);
    void delete(long id);

}
