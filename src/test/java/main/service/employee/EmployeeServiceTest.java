package main.service.employee;

import main.dto.EmployeeDto;
import main.dto.mappers.EmployeeDtoMapper;
import main.model.Employee;
import main.model.EmployeeName;
import main.repository.EmployeeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {
    private final EmployeeRepository employeeRepository =
            Mockito.mock(EmployeeRepository.class);
    private final EmployeeService employeeService =
            new EmployeeService(employeeRepository);

    @Test
    @DisplayName("Test getById if employee found")
    public void testGetByIdIfEmployeeFound(){
        int employeeId = 2;
        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setEmployeeName(
                new EmployeeName(employeeId,"Surname","Name","Patronomic"));
        employee.setSnils("11111111111");
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        ResponseEntity<?> responseEntity = employeeService.findById(employeeId);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
        verify(employeeRepository,times(1)).findById(employeeId);
    }

    @Test
    @DisplayName("Test getById if employee not found")
    public void testGetByIdIfEmployeeNotFound(){
        int employeeId = 3;
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());
        ResponseEntity<?> responseEntity = employeeService.findById(employeeId);
        assertEquals("404 NOT_FOUND", responseEntity.getStatusCode().toString());
        verify(employeeRepository,times(1)).findById(employeeId);
    }

    @Test
    @DisplayName("Test save if  employee already exists")
    public void testSaveIfEmployeeAlreadyExists(){
        int employeeId = 3;
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employeeId);
        employeeDto.setName("Name");
        employeeDto.setSurname("Surname");
        employeeDto.setPatronymic("Patronymic");
        employeeDto.setSnils("12345678910");
        Employee employee = EmployeeDtoMapper.mapToEntity(employeeDto);
        when(employeeRepository.findBySnils(employeeDto.getSnils())).thenReturn(employee);
        ResponseEntity<?> responseEntity = employeeService.save(employeeDto);
        assertEquals("422 UNPROCESSABLE_ENTITY", responseEntity.getStatusCode().toString());
        verify(employeeRepository,times(1))
                .findBySnils(employeeDto.getSnils());
    }

    @Test
    @DisplayName("Test save if  created new employee")
    public void testSaveIfCreatedNewEmployee(){
        int employeeId = 3;
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employeeId);
        employeeDto.setName("Name");
        employeeDto.setSurname("Surname");
        employeeDto.setPatronymic("Patronymic");
        employeeDto.setSnils("12345678910");
        when(employeeRepository.findBySnils(employeeDto.getSnils())).thenReturn(null);
        ResponseEntity<?> responseEntity = employeeService.save(employeeDto);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }
}
