package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.*;
import main.dto.rest.EmployeeDto;
import main.dto.rest.RoleDto;
import main.dto.rest.UserDto;
import main.model.Employee;
import main.model.Role;
import main.model.User;
import main.repository.EmployeeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class EmployeeServiceTest {
    private final EmployeeRepository employeeRepository = Mockito.mock(EmployeeRepository.class);
    private final EmployeeServiceImpl employeeService = new EmployeeServiceImpl(employeeRepository);
    private final Pageable pageable = PageRequest.of(1,10, Sort.by(Sort.Direction.ASC, "surname"));
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();


    @Test
    @DisplayName("Test save")
    public void testSaveIfEmployeeSurnameIsNull(){
        employeeService.save(new Employee());
        verify(employeeRepository,times(1)).save(any(Employee.class));
    }

    @Test
    @DisplayName("Test findById if employee not found")
    public void testFindByIdIfEmployeeNotFound(){
        long employeeId = 3L;
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()-> employeeService.findById(employeeId));
        }

    @Test
    @DisplayName("Test findById if employee found")
    public void testFindByIdIfEmployeeFound(){
        long employeeId = 5L;
        Employee employee = new Employee();
        User user = new User();
        user.setChecked(true);
        user.setEnabled(true);
        user.setId(employeeId);
        user.setRoles(Set.of(new Role()));
        employee.setUser(user);
        employee.setId(employeeId);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        EmployeeDto employeeDto = employeeService.findById(employeeId);
        assertEquals(employeeId, employeeDto.getId());
    }

    @Test
    @DisplayName("Test getEmployeeById if employee found")
    public void testSaveIfEmployeeSnilsIsNotCorrected(){
        long employeeId = 5L;
        Employee employee = new Employee();
        User user = new User();
        user.setChecked(true);
        user.setEnabled(true);
        user.setId(employeeId);
        user.setRoles(Set.of(new Role()));
        employee.setUser(user);
        employee.setId(employeeId);
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employee));
        Employee actualEmployee = employeeService.getEmployeeById(employeeId);
        assertEquals(employee.getId(), actualEmployee.getId());
    }

    @Test
    @DisplayName("Test getEmployeeById if employee not found")
    public void testSaveIfCreatedNewEmployee(){
        long employeeId = 5L;
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()->employeeService.getEmployeeById(employeeId));
    }

    @Test
    @DisplayName("Test findBySurname pageable")
    public void testFindBySurnamePageable(){
        String searchString = "surname";
        User user = new User();
        user.setSurname(searchString);
        user.setId(3L);
        user.setChecked(true);
        user.setEnabled(true);
        user.setRoles(Set.of(new Role()));
        Employee employee = new Employee();
        employee.setUser(user);
        employee.setId(3L);
        Page<Employee> page = new PageImpl<>(List.of(employee),
                pageable,1L);
        when(employeeRepository.findBySurname(searchString, pageable)).thenReturn(page);
        Page<EmployeeDto> actualPage = employeeService.findBySurname(searchString, pageable);
        assertEquals(page.getContent().get(0).getId(),actualPage.getContent().get(0).getId());
        verify(employeeRepository, times(1)).findBySurname(searchString, pageable);
    }

    @Test
    @DisplayName("Test findBySurname")
    public void testFindBySurname(){
        String searchString = "surname";
        User user = new User();
        user.setSurname(searchString);
        user.setId(3L);
        user.setChecked(true);
        user.setEnabled(true);
        user.setRoles(Set.of(new Role()));
        Employee employee = new Employee();
        employee.setUser(user);
        employee.setId(user.getId());
        when(employeeRepository.findBySurname(searchString)).thenReturn(List.of(employee));
        List<EmployeeDto> actualList = employeeService.findBySurname(searchString);
        assertEquals(employee.getId(), actualList.get(0).getId());
    }

    @Test
    @DisplayName("Test findAll pageable")
    public void testUpdateIfEmployeeSurnameIsNull(){
        User user = new User();
        user.setId(5L);
        user.setEnabled(true);
        user.setChecked(true);
        user.setRoles(Set.of(new Role()));
        Employee employee = new Employee();
        employee.setId(user.getId());
        employee.setUser(user);
        Page<Employee> page = new PageImpl<>(List.of(employee));
        when(employeeRepository.findAll(pageable)).thenReturn(page);
        Page<EmployeeDto> actualPage = employeeService.findAll(pageable);
        assertEquals(user.getId(), actualPage.getContent().get(0).getId());
    }

    @Test
    @DisplayName("Test findAll")
    public void testFindAll(){
        User user = new User();
        user.setRoles(Set.of(new Role()));
        user.setId(5L);
        user.setEnabled(true);
        user.setChecked(true);
        Employee employee = new Employee();
        employee.setId(user.getId());
        employee.setUser(user);
        when(employeeRepository.findAll()).thenReturn(List.of(employee));
        List<EmployeeDto> actualList = employeeService.findAll();
        assertEquals(user.getId(), actualList.get(0).getId());
    }


    @Test
    @DisplayName("Test update if employeeDto user is null")
    public void testUpdateIfEmployeeDtoUserIsNull(){
        long employeeId = 5L;
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(5L);
        UserDto userDto = new UserDto();
        userDto.setId(employeeId);
        employeeDto.setUserDto(null);
        employeeDto.setSnils("11111111111");
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());
        Set<ConstraintViolation<EmployeeDto>> violation = validator.validate(employeeDto);
        assertFalse(violation.isEmpty());
        }

    @Test
    @DisplayName("Test update if employeeDto snils is not correct")
    public void testUpdateIfEntityNotFound(){
        long employeeId = 5L;
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(5L);
        UserDto userDto = new UserDto();
        userDto.setId(employeeId);
        employeeDto.setUserDto(userDto);
        employeeDto.setSnils("1111111111");
        Set<ConstraintViolation<EmployeeDto>> violation = validator.validate(employeeDto);
        assertFalse(violation.isEmpty());
    }

    @Test
    @DisplayName("Test update if employee not found")
    public void testUpdateIfEmployeeNotFound(){
        long employeeId = 5L;
        UserDto userDto = new UserDto();
        userDto.setId(employeeId);
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(5L);
        employeeDto.setUserDto(userDto);
        employeeDto.setSnils("22222222222");
        Set<ConstraintViolation<EmployeeDto>> violations = validator.validate(employeeDto);
        assertTrue(violations.isEmpty());
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, ()->employeeService.update(employeeDto));
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    @DisplayName("Test update if employee found")
    public void testUpdateIfEmployeeFound(){
       long employeeId = 5L;
       UserDto userDto = new UserDto();
       userDto.setId(employeeId);
       userDto.setRoles(Set.of(new RoleDto()));
       EmployeeDto employeeDto = new EmployeeDto();
       employeeDto.setId(employeeId);
       employeeDto.setUserDto(userDto);
       when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(new Employee()));
       employeeService.update(employeeDto);
       verify(employeeRepository,times(1)).save(any(Employee.class));
    }

    @Test
    @DisplayName("Test delete")
    public void testDelete(){
        long employeeId = 7L;
        employeeService.delete(employeeId);
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }


}
