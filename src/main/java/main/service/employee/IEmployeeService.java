package main.service.employee;

import main.dto.EmployeeDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IEmployeeService {
    ResponseEntity<?> save(EmployeeDto employeeDto);
    ResponseEntity<?> findById(int id);
    List<EmployeeDto> findAll();
    ResponseEntity<?> update(EmployeeDto employeeDto);
    ResponseEntity<?>delete(int id);

}
