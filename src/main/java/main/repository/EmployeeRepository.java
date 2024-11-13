package main.repository;

import lombok.NonNull;
import main.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    Employee findBySnils(String snils);

    @Query(value = "select * " +
            "from employee e, system_users su" +
            "where e.id = su.id " +
            "and  su.surname ilike '%?1%';",
            countQuery = "select count(*) " +
            "from employee e, system_users su" +
            "where e.id = su.id " +
            "and  su.surname ilike '%?1%';",
            nativeQuery = true)
    Page<Employee> findBySurname(String surname, Pageable pageable);

    @Query(value =
            "select * " +
            "from employee e, system_users su" +
            "where e.id = su.id " +
            "and  su.surname ilike '%?1%';", nativeQuery = true)
    List<Employee> findBySurname(String surname);
    @Override
    @NonNull
    Page<Employee> findAll(@NonNull Pageable pageable);
}
