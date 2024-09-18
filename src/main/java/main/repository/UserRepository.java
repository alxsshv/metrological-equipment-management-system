package main.repository;

import main.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>
{
    User findByUsername(String username);
    List<User> findBySurname(String surname);
    Page<User> findBySurnameContainingOrUsernameContaining(String surname, String username, Pageable pageable);
}
