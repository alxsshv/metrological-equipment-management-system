package main.service.interfaces;

import main.dto.rest.EmployeeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IEmployeeService {
    ResponseEntity<?> save(EmployeeDto employeeDto);
    ResponseEntity<?> findById(long id);
    ResponseEntity<?> findBySurname(String surname, Pageable pageable);
    Page<EmployeeDto> findAll(Pageable pageable);
    List<EmployeeDto> findAll();
    ResponseEntity<?> update(EmployeeDto employeeDto);
    ResponseEntity<?>delete(long id);

}
