package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import main.dto.rest.EmployeeDto;
import main.dto.rest.mappers.EmployeeDtoMapper;
import main.model.Employee;
import main.repository.EmployeeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static testutils.TestEntityGenerator.*;

public class EmployeeServiceTest {
    private final EmployeeRepository employeeRepository =
            Mockito.mock(EmployeeRepository.class);
    private final EmployeeServiceImpl employeeService =
            new EmployeeServiceImpl(employeeRepository);
    private final Pageable pageable = PageRequest.of(1,10, Sort.by(Sort.Direction.ASC, "surname"));

    @Test
    @DisplayName("Test save if employee already exists")
    public void testSaveIfEmployeeAlreadyExists(){
        EmployeeDto employeeDto = generateEmployeeDto(5L);
        Employee employee = EmployeeDtoMapper.mapToEntity(employeeDto);
        when(employeeRepository.findBySnils(employeeDto.getSnils())).thenReturn(employee);
        ResponseEntity<?> responseEntity = employeeService.save(employeeDto);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(employeeRepository,times(1)).findBySnils(employeeDto.getSnils());
    }

    @Test
    @DisplayName("Test save if employee surname is null")
    public void testSaveIfEmployeeSurnameIsNull(){
        EmployeeDto employeeDto = generateEmployeeDto(5L);
        employeeDto.setSurname(null);
        ResponseEntity<?> responseEntity = employeeService.save(employeeDto);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(employeeRepository,never()).findBySnils(employeeDto.getSnils());
        verify(employeeRepository,never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("Test save if employee name is null")
    public void testSaveIfEmployeeNameIsNull(){
        EmployeeDto employeeDto = generateEmployeeDto(5L);
        employeeDto.setName(null);
        ResponseEntity<?> responseEntity = employeeService.save(employeeDto);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(employeeRepository,never()).findBySnils(employeeDto.getSnils());
        verify(employeeRepository,never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("Test save if employee patronymic is null")
    public void testSaveIfEmployeePatronymicIsNull(){
        EmployeeDto employeeDto = generateEmployeeDto(5L);
        employeeDto.setPatronymic(null);
        ResponseEntity<?> responseEntity = employeeService.save(employeeDto);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(employeeRepository,never()).findBySnils(employeeDto.getSnils());
        verify(employeeRepository,never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("Test save if employee snils is not corrected")
    public void testSaveIfEmployeeSnilsIsNotCorrected(){
        EmployeeDto employeeDto = generateEmployeeDto(5L);
        employeeDto.setSnils("1234567-89");
        ResponseEntity<?> responseEntity = employeeService.save(employeeDto);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(employeeRepository,never()).findBySnils(employeeDto.getSnils());
        verify(employeeRepository,never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("Test save if created new employee")
    public void testSaveIfCreatedNewEmployee(){
        EmployeeDto employeeDto = generateEmployeeDto(5L);
        when(employeeRepository.findBySnils(employeeDto.getSnils())).thenReturn(null);
        ResponseEntity<?> responseEntity = employeeService.save(employeeDto);
        assertEquals("201 CREATED", responseEntity.getStatusCode().toString());
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    @DisplayName("Test update If employee found")
    public void testUpdateIfEmployeeFound(){
        EmployeeDto employeeDto = generateEmployeeDto(5L);
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
        EmployeeDto employeeDto = generateEmployeeDto(employeeId);
        employeeDto.setSurname(null);
        Employee employee = EmployeeDtoMapper.mapToEntity(employeeDto);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        ResponseEntity<?> responseEntity = employeeService.update(employeeDto);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(employeeRepository,never()).findById(employeeId);
        verify(employeeRepository,never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("Test update if employee name is null")
    public void testUpdateIfEmployeeNameIsNull(){
        long employeeId = 3L;
        EmployeeDto employeeDto = generateEmployeeDto(employeeId);
        employeeDto.setName(null);
        Employee employee = EmployeeDtoMapper.mapToEntity(employeeDto);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        ResponseEntity<?> responseEntity = employeeService.update(employeeDto);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(employeeRepository,never()).findById(employeeId);
        verify(employeeRepository,never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("Test update if employee patronymic is null")
    public void testUpdateIfEmployeePatronymicIsNull(){
        long employeeId = 3L;
        EmployeeDto employeeDto = generateEmployeeDto(employeeId);
        employeeDto.setPatronymic(null);
        Employee employee = EmployeeDtoMapper.mapToEntity(employeeDto);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        ResponseEntity<?> responseEntity = employeeService.update(employeeDto);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(employeeRepository,never()).findById(employeeId);
        verify(employeeRepository,never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("Test update if employee snils is not corrected")
    public void testUpdateIfEmployeeSnilsIsNotCorrected(){
        long employeeId = 3L;
        EmployeeDto employeeDto = generateEmployeeDto(employeeId);
        employeeDto.setSnils("1234567-89");
        Employee employee = EmployeeDtoMapper.mapToEntity(employeeDto);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        ResponseEntity<?> responseEntity = employeeService.update(employeeDto);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(employeeRepository,never()).findById(employeeId);
        verify(employeeRepository,never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("Test update If employee not found")
    public void testUpdateIfEmployeeNotFound(){
        long employeeId = 3L;
        EmployeeDto employeeDto = generateEmployeeDto(employeeId);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());
        ResponseEntity<?> responseEntity = employeeService.update(employeeDto);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
    }

    @Test
    @DisplayName("Test delete")
    public void testDelete(){
        long employeeId = 2L;
        Employee employee = generateEmployee(employeeId);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        ResponseEntity<?> responseEntity = employeeService.delete(employeeId);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
        verify(employeeRepository, times(1)).deleteById(2L);
    }


    @Test
    @DisplayName("Test findById if employee found")
    public void testFindByIdIfEmployeeFound(){
        long employeeId = 2L;
        Employee employee = generateEmployee(employeeId);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        ResponseEntity<?> responseEntity = employeeService.findById(employeeId);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
        verify(employeeRepository,times(1)).findById(employeeId);
    }

    @Test
    @DisplayName("Test findById if employee not found")
    public void testFindByIdIfEmployeeNotFound(){
        long employeeId = 3L;
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());
        ResponseEntity<?> responseEntity = employeeService.findById(employeeId);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(employeeRepository,times(1)).findById(employeeId);
    }

    @Test
    @DisplayName("test getEmployeeById if entity not found")
    public void testGetEmployeeByIdIfEntityNotFound() throws EntityNotFoundException {
        long employeeId = 1L;
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class,()->
                employeeService.getEmployeeById(employeeId)) ;
        assertEquals("Поверитель № 1 не найден",exception.getMessage());
        verify(employeeRepository,times(1)).findById(employeeId);
    }

    @Test
    @DisplayName("test getEmployeeById if entity found")
    public void testGetEmployeeByIdIfEntityFound() throws EntityNotFoundException {
        long employeeId = 1L;
        Employee employee = generateEmployee(employeeId);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        Employee employeeFromDb = employeeService.getEmployeeById(employeeId);
        assertEquals(employee, employeeFromDb);
        verify(employeeRepository,times(1)).findById(employeeId);
    }

    @Test
    @DisplayName("Test find by surname when Entity is found")
    public void testFindBySurname(){
        Employee employee = generateEmployee(2L);
        List<Employee> employees = List.of(employee);
        long totalEmployees = 10L;
        Page<Employee> page = new PageImpl<>(employees,pageable,totalEmployees);
        when(employeeRepository.findBySurnameIgnoreCaseContaining(employee.getSurname(), pageable)).thenReturn(page);
        ResponseEntity<?> responseEntity = employeeService.findBySurname(employee.getSurname(), pageable);
        assertEquals("200 OK",responseEntity.getStatusCode().toString());
        verify(employeeRepository,times(1)).findBySurnameIgnoreCaseContaining(employee.getSurname(), pageable);
    }

    @Test
    @DisplayName("Test find All without pageable")
    public void testWithoutPageableFindAll(){
        Employee employee = generateEmployee(2L);
        List<Employee> employees = List.of(employee);
        when(employeeRepository.findAll()).thenReturn(employees);
        List<EmployeeDto> employeeDtos = employeeService.findAll();
        assertEquals(employees.size(),employeeDtos.size());
        verify(employeeRepository,times(1)).findAll();
    }

    @Test
    @DisplayName("Test find All with pageable")
    public void testPageableFindAll(){
        Employee employee = generateEmployee(2L);
        List<Employee> employees = List.of(employee);
        long totalPages = 1L;
        Page<Employee> page = new PageImpl<>(employees,pageable,totalPages);
        when(employeeRepository.findAll(pageable)).thenReturn(page);
        Page<EmployeeDto> employeeDtos = employeeService.findAll(pageable);
        assertEquals(employees.size(),employeeDtos.getContent().size());
        verify(employeeRepository,times(1)).findAll(pageable);
    }

}
