package main.service.interfaces;

import main.dto.rest.EmployeeDto;
import main.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmployeeService {
    ResponseEntity<?> save(EmployeeDto employeeDto);
    ResponseEntity<?> findById(long id);
    Employee getEmployeeById(long id);
    ResponseEntity<?> findBySurname(String surname, Pageable pageable);
    ResponseEntity<?> findBySurname(String surname);
    Page<EmployeeDto> findAll(Pageable pageable);
    List<EmployeeDto> findAll();
    ResponseEntity<?> update(EmployeeDto employeeDto);
    ResponseEntity<?>delete(long id);

}
