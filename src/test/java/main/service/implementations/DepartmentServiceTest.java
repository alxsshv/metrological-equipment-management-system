package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.*;
import main.dto.rest.DepartmentDto;
import main.model.Department;
import main.repository.DepartmentRepository;
import main.service.interfaces.DepartmentService;
import main.service.validators.DepartmentAlreadyExistValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;


import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class DepartmentServiceTest {
    private final DepartmentRepository departmentRepository =
            Mockito.mock(DepartmentRepository.class);
    private final DepartmentService departmentService =
            new DepartmentServiceImpl(departmentRepository);
    private final Pageable pageable = PageRequest.of(1,10, Sort.by(Sort.Direction.ASC, "notation"));
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();
    @Autowired
    ConstraintValidatorContext constraintValidatorContext;



    @Test
    @DisplayName("Test save if department notation is null")
    public void testSaveIfDepartmentIfNotationIsNull(){
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setNotation(null);
        Set<ConstraintViolation<DepartmentDto>> violations = validator.validate(departmentDto);
        assertEquals(1,violations.size());
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Test save if department notation is empty")
    public void testSaveIfDepartmentNotationIsEmpty(){
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setNotation("");
        Set<ConstraintViolation<DepartmentDto>> violations = validator.validate(departmentDto);
        assertEquals(1,violations.size());
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Test save if department already exist")
    public void testSaveIfDepartmentAlreadyExist(){
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setNotation("Отдел");
        Department department = new ModelMapper().map(departmentDto, Department.class);
        when(departmentRepository.findByNotation(departmentDto.getNotation())).thenReturn(department);
        boolean departmentNotExist = new DepartmentAlreadyExistValidator(departmentRepository).isValid(departmentDto, constraintValidatorContext);
        assertFalse(departmentNotExist);
    }

    @Test
    @DisplayName("Test save if created new department")
    public void testSaveIfCreatedNewDepartment(){
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setNotation("Отдел");
        when(departmentRepository.findByNotation(departmentDto.getNotation())).thenReturn(null);
        boolean departmentNotExist = new DepartmentAlreadyExistValidator(departmentRepository).isValid(departmentDto, constraintValidatorContext);
        departmentService.save(departmentDto);
        assertTrue(departmentNotExist);
        verify(departmentRepository, times(1)).save(any(Department.class));

    }

    @Test
    @DisplayName("Test getById if department found")
    public void testGetByIdIfDepartmentFound(){
        long departmentId = 1L;
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setNotation("Отдел");
        Department department = new ModelMapper().map(departmentDto, Department.class);
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        assertEquals(department, departmentService.getById(departmentId));
        verify(departmentRepository,times(1)).findById(departmentId);
    }

    @Test
    @DisplayName("Test getById if department if not found")
    public void testGetByIdIfDepartmentIfNotFound(){
        long departmentId = 1L;
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, ()-> departmentService.getById(departmentId));
    }

    @Test
    @DisplayName("Test findByNotation (pageable) if department is found")
    public void testFindByNotationPageableIfDepartmentIsFound(){
        int totalPage = 1;
        DepartmentDto departmentDto = new DepartmentDto();
        String notation = "Отдел";
        departmentDto.setNotation(notation);
        Set<ConstraintViolation<DepartmentDto>> violations = validator.validate(departmentDto);
        assertTrue(violations.isEmpty());
        Page<Department> page = new PageImpl<>(List.of(new Department(), new Department()),pageable, totalPage);
        when(departmentRepository.findByNotationIgnoreCaseContaining(notation,pageable)).thenReturn(page);
        Page<DepartmentDto> actualPage = departmentService.findByNotation(notation,pageable);
        assertEquals(2, actualPage.getContent().size());
        verify(departmentRepository, times(1)).findByNotationIgnoreCaseContaining(notation,pageable);
    }

    @Test
    @DisplayName("Test findByNotation if notation is empty")
    public void testFindByNotationIfNotationIsEmpty(){
        DepartmentDto departmentDto = new DepartmentDto();
        String notation = "";
        departmentDto.setNotation(notation);
        Set<ConstraintViolation<DepartmentDto>> violations = validator.validate(departmentDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Test findByNotation if department is found")
    public void testFindByNotationIfDepartmentIsFound(){
        DepartmentDto departmentDto = new DepartmentDto();
        String notation = "Отдел";
        departmentDto.setNotation(notation);
        Set<ConstraintViolation<DepartmentDto>> violations = validator.validate(departmentDto);
        assertTrue(violations.isEmpty());
        when(departmentRepository.findByNotationIgnoreCaseContaining(notation)).thenReturn(List.of(new Department()));
        List<DepartmentDto> expected = departmentService.findByNotation(notation);
        assertEquals(expected.size(), 1);
        verify(departmentRepository,times(1)).findByNotationIgnoreCaseContaining(any(String.class));
    }

    @Test
    @DisplayName("Test findAll (pageable)")
    public void findAllPageable(){
        Page<Department> page = new PageImpl<>(List.of(new Department(), new Department()),pageable,1);
        when(departmentRepository.findAll(pageable)).thenReturn(page);
        Page<DepartmentDto> actualPage = departmentService.findAll(pageable);
        assertEquals(2, actualPage.getContent().size());
        verify(departmentRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Test findAll")
    public void findAll(){
        when(departmentRepository.findAll()).thenReturn(List.of(new Department(), new Department()));
        List<DepartmentDto> actualList = departmentService.findAll();
        assertEquals(2, actualList.size());
        verify(departmentRepository,times(1)).findAll();
    }

    @Test
    @DisplayName("Test update if department found")
    public void testUpdateIfDepartmentFound(){
        long departmentId =  1L;
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(departmentId);
        departmentDto.setNotation("Отдел");
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(new Department()));
        departmentService.update(departmentDto);
        verify(departmentRepository,times(1)).save(any(Department.class));
    }

    @Test
    @DisplayName("Test update if department not found")
    public void testUpdateIfDepartmentNotFound(){
        long departmentId = 1L;
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(departmentId);
        departmentDto.setNotation("Отдел");
        when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, ()-> departmentService.update(departmentDto));
        verify(departmentRepository, never()).save(any(Department.class));
    }

    @Test
    @DisplayName("Test delete")
    public void testDelete() {
        long departmentId = 1L;
        departmentService.delete(departmentId);
        verify(departmentRepository, times(1)).deleteById(departmentId);
    }
}
