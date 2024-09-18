package main.service.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import main.dto.rest.UserDto;
import main.model.User;
import main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
public class UserAlreadyExistValidator implements ConstraintValidator<UserAlreadyExist, UserDto> {
    @Autowired
    private final UserRepository userRepository;

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext constraintValidatorContext) {
        User userFromDb = userRepository.findByUsername(userDto.getUsername());
        return userFromDb == null;
    }

}
