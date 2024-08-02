package main.repository;

import lombok.NonNull;
import main.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    Employee findBySnils(String snils);
    Page<Employee> findBySurnameIgnoreCaseContaining(String surname, Pageable pageable);
    List<Employee> findBySurnameIgnoreCaseContaining(String surname);
    @Override
    @NonNull
    Page<Employee> findAll(@NonNull Pageable pageable);
}
