package main.service.interfaces;

import main.dto.rest.DepartmentDto;
import main.model.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DepartmentService {
    void save(DepartmentDto departmentDto);
    DepartmentDto findById(long id);
    Department getById(long id);
    Page<DepartmentDto> findByNotation(String notation, Pageable pageable);
    List<DepartmentDto> findByNotation(String notation);
    Page<DepartmentDto> findAll(Pageable pageable);
    List<DepartmentDto> findAll();
    void update(DepartmentDto departmentDto);
    void delete(long id);

}
