package main.service.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import main.model.Role;
import main.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
public class RoleAlreadyExistValidator implements ConstraintValidator<RoleAlreadyExist, Role> {
    @Autowired
    private final RoleRepository roleRepository;

    @Override
    public boolean isValid(Role role, ConstraintValidatorContext constraintValidatorContext) {
        Role roleFromDb = roleRepository.findByName(role.getName());
        return roleFromDb == null;
    }

}
