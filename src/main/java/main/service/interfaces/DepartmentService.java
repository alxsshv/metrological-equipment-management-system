package main.service.interfaces;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import main.dto.rest.DepartmentDto;
import main.model.Department;
import main.service.validators.DepartmentAlreadyExist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface DepartmentService {
    void save(@DepartmentAlreadyExist @Valid DepartmentDto departmentDto);
    DepartmentDto findById(long id);
    Department getById(long id);
    Page<DepartmentDto> findByNotation(@NotBlank(message = "Поле для поиска не может быть пустым") String notation, Pageable pageable);
    List<DepartmentDto> findByNotation(@NotBlank(message = "Поле для поиска не может быть пустым") String notation);
    Page<DepartmentDto> findAll(Pageable pageable);
    List<DepartmentDto> findAll();
    void update(@Valid DepartmentDto departmentDto);
    void delete(long id);

}
