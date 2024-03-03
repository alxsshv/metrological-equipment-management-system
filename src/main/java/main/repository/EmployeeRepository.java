package main.repository;

import main.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    Employee findBySnils(String snils);
    Page<Employee> findBySurname(String surname, Pageable pageable);
    @Override
    Page<Employee> findAll(Pageable pageable);
}
