package main.service.implementations;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import main.dto.rest.UserDto;
import main.dto.rest.mappers.UserDtoMapper;
import main.model.User;
import main.repository.RoleRepository;
import main.repository.UserRepository;
import main.service.interfaces.UserService;
import main.service.validators.UserAlreadyExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
@RequiredArgsConstructor
@Service
@Validated
public class UserServiceImpl implements UserService, UserDetailsService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("Пользователь с таким логином не найден");
        }
        return user;
    }


    @Override
    public void save(@UserAlreadyExist @Valid UserDto userDto) {
        User user = UserDtoMapper.mapToEntity(userDto);
        userRepository.save(user);
    }

    @Override
    public UserDto findById(long id) {
        User user = getUserById(id);
        return UserDtoMapper.mapToDto(user);
    }

    @Override
    public User getUserById(long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) {
            throw new EntityNotFoundException("Пользователь с id " + id + " не найден");
        }
        return userOpt.get();
    }

    @Override
    public Page<UserDto> findBySearchString(
            @NotBlank(message = "Поле для поиска не может быть пустым") String searchString, Pageable pageable) {
        return userRepository.findBySurnameContainingOrUsernameContaining(searchString, searchString, pageable)
                .map(UserDtoMapper::mapToDto);
    }

    @Override
    public List<UserDto> findBySearchString(@NotBlank(message = "Поле для поиска не может быть пустым") String surname) {
        return userRepository.findBySurname(surname).stream().map(UserDtoMapper::mapToDto).toList();
    }

    @Override
    public Page<UserDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserDtoMapper::mapToDto);
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(UserDtoMapper::mapToDto).toList();
    }

    @Override
    public void update(@Valid UserDto userDto) {
        User user = getUserById(userDto.getId());
        User updatingUserData = UserDtoMapper.mapToEntity(userDto);
        user.updateFrom(updatingUserData);
        userRepository.save(user);
    }

    @Override
    public void delete(long id) {
        //TODO: реализовать проверку на дефолтное значение, запись по умолчанию, её удалять нельзя
        userRepository.deleteById(id);
    }
}
