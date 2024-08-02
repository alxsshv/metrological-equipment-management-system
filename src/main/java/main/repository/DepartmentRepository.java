package main.repository;

import main.model.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Department findByNotation(String notation);
    Page<Department> findByNotationIgnoreCaseContaining(String notation, Pageable pageable);
    List<Department> findByNotationIgnoreCaseContaining(String notation);
}
