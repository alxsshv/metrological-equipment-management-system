package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import main.dto.rest.EmployeeDto;
import main.dto.rest.mappers.EmployeeDtoMapper;
import main.model.Employee;
import main.repository.EmployeeRepository;
import main.service.interfaces.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Validated
public class EmployeeServiceImpl implements EmployeeService {
    public static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private final EmployeeRepository employeeRepository;

    @Override
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }


    @Override
    public EmployeeDto findById(long id) {
            Employee employee = getEmployeeById(id);
            return EmployeeDtoMapper.mapToDto(employee);
    }

    public Employee getEmployeeById(long id) {
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        if (employeeOpt.isEmpty()) {
            throw new EntityNotFoundException("Поверитель № " + id + " не найден");
        }
        return employeeOpt.get();
    }


    @Override
    public Page<EmployeeDto> findBySurname(@NotBlank(message = "Поле для поиска не может быть пустым") String surname, Pageable pageable) {
            return employeeRepository.findBySurname(surname, pageable)
                    .map(e -> {e.setSnils("Указан");return e;}).map(EmployeeDtoMapper::mapToDto);
    }
    @Override
    public List<EmployeeDto>  findBySurname(@NotBlank(message = "Поле для поиска не может быть пустым") String surname) {
            return employeeRepository.findBySurname(surname).stream().peek(e -> e.setSnils("Указан"))
                    .map(EmployeeDtoMapper::mapToDto).toList();
    }


    @Override
    public Page<EmployeeDto> findAll(Pageable pageable) {
        return employeeRepository.findAll(pageable).map(e->{e.setSnils("Указан"); return e;}).map(EmployeeDtoMapper::mapToDto);
    }

    @Override
    public List<EmployeeDto> findAll() {
        return employeeRepository.findAll().stream().peek(e -> e.setSnils("Указан")).map(EmployeeDtoMapper ::mapToDto).toList();
    }

    @Override
    public void update(@Valid EmployeeDto employeeDto){
            Employee employee = getEmployeeById(employeeDto.getId());
            Employee updatingEmployeeData = EmployeeDtoMapper.mapToEntity(employeeDto);
            employee.updateFrom(updatingEmployeeData);
            employeeRepository.save(employee);
    }

    @Override
    public void delete(long id){
        employeeRepository.deleteById(id);
    }

}
