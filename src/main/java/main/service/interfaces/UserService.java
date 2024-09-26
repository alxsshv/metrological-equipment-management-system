package main.service.interfaces;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import main.dto.rest.UserDto;
import main.model.User;
import main.service.validators.UserAlreadyExist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;


import java.util.List;


public interface UserService extends UserDetailsService {
    void save(@UserAlreadyExist @Valid UserDto userDto);
    UserDto findById(long id);
    User getUserById(long id);
    Page<UserDto> findBySearchString(@NotBlank(message = "Поле для поиска не может быть пустым") String searchString, Pageable pageable);
    List<UserDto> findBySearchString(@NotBlank(message = "Поле для поиска не может быть пустым") String searchString);
    Page<UserDto> findAll(Pageable pageable);
    List<UserDto> findAll();
    Page<UserDto> findAllWaitingCheck(Pageable pageable);
    long findWaitingCheckUsersCount();
    void update(@Valid UserDto userDto);
    void delete(long id);

}
