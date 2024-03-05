package main.repository;

import lombok.NonNull;
import main.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    Employee findBySnils(String snils);
    Page<Employee> findBySurname(String surname, Pageable pageable);
    @Override
    @NonNull
    Page<Employee> findAll(@NonNull Pageable pageable);
}
