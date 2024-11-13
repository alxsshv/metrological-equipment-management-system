package main.service.implementations;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import main.config.AppDefaults;
import main.config.utils.SystemSecurityRoles;
import main.dto.rest.EmployeeDto;
import main.dto.rest.mappers.EmployeeDtoMapper;
import main.dto.rest.mappers.UserDtoMapper;
import main.model.Employee;
import main.model.Role;
import main.model.User;
import main.service.interfaces.EmployeeService;
import main.service.interfaces.EmployeeServiceFacade;
import main.service.interfaces.RoleService;
import main.service.interfaces.UserService;
import main.service.validators.EmployeeAlreadyExist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class EmployeeServiceFacadeImpl implements EmployeeServiceFacade {
    private final EmployeeService employeeService;
    private final UserService userService;
    private final RoleService roleService;

    @Override
    public void save(@EmployeeAlreadyExist @Valid EmployeeDto employeeDto) {
        Role verificationEmployee = roleService.getRoleByName(SystemSecurityRoles.VERIFICATION_EMPLOYEE.getName());
        User user = userService.getUserById(employeeDto.getUserDto().getId());
        addRoleForUser(user, verificationEmployee);
        Employee employee = EmployeeDtoMapper.mapToEntity(employeeDto);
        employee.setUser(user);
        employeeService.save(employee);
    }

    private void addRoleForUser(User user, Role role){
        user.getRoles().add(role);
        userService.update(UserDtoMapper.mapToDto(user));
    }

    @Override
    public EmployeeDto findById(long id) {
        return employeeService.findById(id);
    }

    @Override
    public Employee getEmployeeById(long id) {
        return employeeService.getEmployeeById(id);
    }

    @Override
    public Page<EmployeeDto> findBySurname(String surname, Pageable pageable) {
        return employeeService.findBySurname(surname, pageable);
    }

    @Override
    public Page<EmployeeDto> findAll(Pageable pageable) {
        return employeeService.findAll(pageable);
    }

    @Override
    public List<EmployeeDto> findAll() {
        return employeeService.findAll();
    }

    @Override
    public void update(EmployeeDto employeeDto) {
        employeeService.update(employeeDto);
    }

    @Override
    public void delete(long id) {
        Role verificationEmployee = roleService.getRoleByName(SystemSecurityRoles.VERIFICATION_EMPLOYEE.getName());
        User user = userService.getUserById(id);
        removeRoleForUser(user, verificationEmployee);
        employeeService.delete(id);
    }

    private void removeRoleForUser(User user,Role role){
        user.getRoles().remove(role);
        if (user.getRoles().isEmpty()){
            user.getRoles().add(AppDefaults.getDefaultUserRole());
        }
        user.setRoles(user.getRoles());
        userService.update(UserDtoMapper.mapToDto(user));
    }
}
