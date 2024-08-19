package main.service.implementations;

import jakarta.validation.*;
import main.dto.rest.DepartmentDto;
import main.model.Department;
import main.repository.DepartmentRepository;
import main.service.interfaces.DepartmentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static testutils.TestEntityGenerator.generateDepartmentDto;

public class DepartmentServiceTest {
    private final DepartmentRepository departmentRepository =
            Mockito.mock(DepartmentRepository.class);
    private final DepartmentService departmentService =
            new DepartmentServiceImpl(departmentRepository);
    private final Pageable pageable = PageRequest.of(1,10, Sort.by(Sort.Direction.ASC, "notation"));
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("Test save if department already exists")
    public void testSaveIfDepartmentAlreadyExists(){
        DepartmentDto departmentDto = generateDepartmentDto(5L);
        departmentDto.setNotation(null);
        Department department = new ModelMapper().map(departmentDto, Department.class);
        System.out.println(departmentDto);
        when(departmentRepository.findByNotation(departmentDto.getNotation())).thenReturn(department);
        departmentService.save(departmentDto);
        Set<ConstraintViolation<DepartmentDto>> violations = validator.validate(departmentDto);
        assertEquals(1,violations.size());
    }

}
