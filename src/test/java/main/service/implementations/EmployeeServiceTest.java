package main.service.implementations;

import main.dto.EmployeeDto;
import main.dto.mappers.EmployeeDtoMapper;
import main.model.Employee;
import main.repository.EmployeeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import testutils.TestDtoGenerator;
import testutils.TestEntityGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {
    private final EmployeeRepository employeeRepository =
            Mockito.mock(EmployeeRepository.class);
    private final EmployeeService employeeService =
            new EmployeeService(employeeRepository);
    private final Pageable pageable = PageRequest.of(1,10, Sort.by(Sort.Direction.ASC, "surname"));

    @Test
    @DisplayName("Test save if employee already exists")
    public void testSaveIfEmployeeAlreadyExists(){
        EmployeeDto employeeDto = TestDtoGenerator.generateEmployeeDto(5L);
        Employee employee = EmployeeDtoMapper.mapToEntity(employeeDto);
        when(employeeRepository.findBySnils(employeeDto.getSnils())).thenReturn(employee);
        ResponseEntity<?> responseEntity = employeeService.save(employeeDto);
        assertEquals("422 UNPROCESSABLE_ENTITY", responseEntity.getStatusCode().toString());
        verify(employeeRepository,times(1)).findBySnils(employeeDto.getSnils());
    }

    @Test
    @DisplayName("Test save if employee surname is null")
    public void testSaveIfEmployeeSurnameIsNull(){
        long employeeId = 5L;
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employeeId);
        employeeDto.setName("Ivan");
        employeeDto.setPatronymic("Ivanovich");
        employeeDto.setSnils("00000000001");
        ResponseEntity<?> responseEntity = employeeService.save(employeeDto);
        assertEquals("422 UNPROCESSABLE_ENTITY", responseEntity.getStatusCode().toString());
        verify(employeeRepository,never()).findBySnils(employeeDto.getSnils());
        verify(employeeRepository,never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("Test save if employee name is null")
    public void testSaveIfEmployeeNameIsNull(){
        long employeeId = 5L;
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employeeId);
        employeeDto.setSurname("Ivanov");
        employeeDto.setPatronymic("Ivanovich");
        employeeDto.setSnils("00000000001");
        ResponseEntity<?> responseEntity = employeeService.save(employeeDto);
        assertEquals("422 UNPROCESSABLE_ENTITY", responseEntity.getStatusCode().toString());
        verify(employeeRepository,never()).findBySnils(employeeDto.getSnils());
        verify(employeeRepository,never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("Test save if employee patronymic is null")
    public void testSaveIfEmployeePatronymicIsNull(){
        long employeeId = 5L;
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employeeId);
        employeeDto.setName("Ivan");
        employeeDto.setSurname("Ivanov");
        employeeDto.setSnils("00000000001");
        ResponseEntity<?> responseEntity = employeeService.save(employeeDto);
        assertEquals("422 UNPROCESSABLE_ENTITY", responseEntity.getStatusCode().toString());
        verify(employeeRepository,never()).findBySnils(employeeDto.getSnils());
        verify(employeeRepository,never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("Test save if employee snils is not corrected")
    public void testSaveIfEmployeeSnilsIsNotCorrected(){
        long employeeId = 3L;
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employeeId);
        employeeDto.setName("Ivan");
        employeeDto.setSurname("Ivanov");
        employeeDto.setPatronymic("Ivanovich");
        employeeDto.setSnils("1234567-89");
        ResponseEntity<?> responseEntity = employeeService.save(employeeDto);
        assertEquals("422 UNPROCESSABLE_ENTITY", responseEntity.getStatusCode().toString());
        verify(employeeRepository,never()).findBySnils(employeeDto.getSnils());
        verify(employeeRepository,never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("Test save if  created new employee")
    public void testSaveIfCreatedNewEmployee(){
        EmployeeDto employeeDto = TestDtoGenerator.generateEmployeeDto(3L);
        when(employeeRepository.findBySnils(employeeDto.getSnils())).thenReturn(null);
        ResponseEntity<?> responseEntity = employeeService.save(employeeDto);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    @DisplayName("Test update If employee found")
    public void testUpdateIfEmployeeFound(){
        EmployeeDto employeeDto = TestDtoGenerator.generateEmployeeDto(3L);
        Employee employee = EmployeeDtoMapper.mapToEntity(employeeDto);
        when(employeeRepository.findById(employeeDto.getId())).thenReturn(Optional.of(employee));
        ResponseEntity<?> responseEntity = employeeService.update(employeeDto);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    @DisplayName("Test update if employee surname is null")
    public void testUpdateIfEmployeeSurnameIsNull(){
        long employeeId = 3L;
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employeeId);
        employeeDto.setName("Ivan");
        employeeDto.setPatronymic("Ivanovich");
        employeeDto.setSnils("12345678910");
        Employee employee = EmployeeDtoMapper.mapToEntity(employeeDto);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        ResponseEntity<?> responseEntity = employeeService.update(employeeDto);
        assertEquals("422 UNPROCESSABLE_ENTITY", responseEntity.getStatusCode().toString());
        verify(employeeRepository,never()).findById(employeeId);
        verify(employeeRepository,never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("Test update if employee name is null")
    public void testUpdateIfEmployeeNameIsNull(){
        long employeeId = 3L;
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employeeId);
        employeeDto.setSurname("Ivanov");
        employeeDto.setPatronymic("Ivanovich");
        employeeDto.setSnils("12345678910");
        Employee employee = EmployeeDtoMapper.mapToEntity(employeeDto);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        ResponseEntity<?> responseEntity = employeeService.update(employeeDto);
        assertEquals("422 UNPROCESSABLE_ENTITY", responseEntity.getStatusCode().toString());
        verify(employeeRepository,never()).findById(employeeId);
        verify(employeeRepository,never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("Test update if employee patronymic is null")
    public void testUpdateIfEmployeePatronymicIsNull(){
        long employeeId = 3L;
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employeeId);
        employeeDto.setName("Ivan");
        employeeDto.setSurname("Ivanov");
        employeeDto.setSnils("12345678910");
        Employee employee = EmployeeDtoMapper.mapToEntity(employeeDto);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        ResponseEntity<?> responseEntity = employeeService.update(employeeDto);
        assertEquals("422 UNPROCESSABLE_ENTITY", responseEntity.getStatusCode().toString());
        verify(employeeRepository,never()).findById(employeeId);
        verify(employeeRepository,never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("Test update if employee snils is not corrected")
    public void testUpdateIfEmployeeSnilsIsNotCorrected(){
        long employeeId = 3L;
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employeeId);
        employeeDto.setName("Ivan");
        employeeDto.setSurname("Ivanov");
        employeeDto.setPatronymic("Ivanovich");
        employeeDto.setSnils("1234567-89");
        Employee employee = EmployeeDtoMapper.mapToEntity(employeeDto);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        ResponseEntity<?> responseEntity = employeeService.update(employeeDto);
        assertEquals("422 UNPROCESSABLE_ENTITY", responseEntity.getStatusCode().toString());
        verify(employeeRepository,never()).findById(employeeId);
        verify(employeeRepository,never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("Test update If employee not found")
    public void testUpdateIfEmployeeNotFound(){
        long employeeId = 3L;
        EmployeeDto employeeDto = TestDtoGenerator.generateEmployeeDto(employeeId);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());
        ResponseEntity<?> responseEntity = employeeService.update(employeeDto);
        assertEquals("404 NOT_FOUND", responseEntity.getStatusCode().toString());
    }

    @Test
    @DisplayName("Test delete if employee found")
    public void testDeleteIfEmployeeFound(){
        long employeeId = 2L;
        Employee employee = TestEntityGenerator.generateEmployeeWithId(employeeId);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        ResponseEntity<?> responseEntity = employeeService.delete(employeeId);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
        verify(employeeRepository, times(1)).delete(employee);
    }

    @Test
    @DisplayName("Test delete if employee not found")
    public void testDeleteIfEmployeeNotFound(){
        long employeeId = 2L;
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());
        ResponseEntity<?> responseEntity = employeeService.delete(employeeId);
        assertEquals("404 NOT_FOUND", responseEntity.getStatusCode().toString());
        verify(employeeRepository, times(1)).findById(employeeId);
    }

    @Test
    @DisplayName("Test get by Id if employee found")
    public void testGetByIdIfEmployeeFound(){
        long employeeId = 2L;
        Employee employee = TestEntityGenerator.generateEmployeeWithId(employeeId);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        ResponseEntity<?> responseEntity = employeeService.findById(employeeId);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
        verify(employeeRepository,times(1)).findById(employeeId);
    }

    @Test
    @DisplayName("Test get by Id if employee not found")
    public void testGetByIdIfEmployeeNotFound(){
        long employeeId = 3L;
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());
        ResponseEntity<?> responseEntity = employeeService.findById(employeeId);
        assertEquals("404 NOT_FOUND", responseEntity.getStatusCode().toString());
        verify(employeeRepository,times(1)).findById(employeeId);
    }
    @Test
    @DisplayName("Test find by surname when surname is found")
    public void testFindBySurname(){
        List<Employee> employees = new ArrayList<>();
        Employee employee = TestEntityGenerator.generateEmployeeWithId(2L);
        employees.add(employee);
        long totalEmployees = 10L;
        Page<Employee> page = new PageImpl<>(employees,pageable,totalEmployees);
        when(employeeRepository.findBySurname(employee.getSurname(), pageable)).thenReturn(page);
        ResponseEntity<?> responseEntity = employeeService.findBySurname(employee.getSurname(), pageable);
        assertEquals("200 OK",responseEntity.getStatusCode().toString());
        verify(employeeRepository,times(1)).findBySurname(employee.getSurname(), pageable);
    }

    @Test
    @DisplayName("Test find All without pageable")
    public void testWithoutPageableFindAll(){
        List<Employee> employees = new ArrayList<>();
        Employee employee = TestEntityGenerator.generateEmployeeWithId(2L);
        employees.add(employee);
        when(employeeRepository.findAll()).thenReturn(employees);
        List<EmployeeDto> employeeDtos = employeeService.findAll();
        assertEquals(employees.size(),employeeDtos.size());
        verify(employeeRepository,times(1)).findAll();
    }
    @Test
    @DisplayName("Test find All with pageable")
    public void testPageableFindAll(){
        List<Employee> employees = new ArrayList<>();
        Employee employee = TestEntityGenerator.generateEmployeeWithId(2L);
        employees.add(employee);
        long totalPages = 1L;
        Page<Employee> page = new PageImpl<>(employees,pageable,totalPages);
        when(employeeRepository.findAll(pageable)).thenReturn(page);
        Page<EmployeeDto> employeeDtos = employeeService.findAll(pageable);
        assertEquals(employees.size(),employeeDtos.getContent().size());
        verify(employeeRepository,times(1)).findAll(pageable);
    }

}
