package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import main.dto.rest.EmployeeDto;
import main.dto.rest.mappers.EmployeeDtoMapper;
import main.exception.DtoCompositionException;
import main.exception.EntityAlreadyExistException;
import main.exception.ParameterNotValidException;
import main.model.Employee;
import main.repository.EmployeeRepository;
import main.service.interfaces.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    public static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private final EmployeeRepository employeeRepository;

    @Override
    public void save(EmployeeDto employeeDto) {
            checkEmployeeDtoComposition(employeeDto);
            validateIfEntityAlreadyExist(employeeDto.getSnils());
            employeeRepository.save(EmployeeDtoMapper.mapToEntity(employeeDto));
    }
    private void checkEmployeeDtoComposition(EmployeeDto dto) throws DtoCompositionException {
        if (dto.getSurname() == null || dto.getSurname().isEmpty()){
            throw new DtoCompositionException("Пожалуйста заполните фамилию поверителя");
        }
        if (dto.getName() == null || dto.getName().isEmpty()){
            throw new DtoCompositionException("Пожалуйста заполните имя поверителя");
        }
        if (dto.getPatronymic() == null || dto.getPatronymic().isEmpty()){
            throw new DtoCompositionException("Пожалуйста заполните отчество поверителя");
        }
        String snilsTemplate = "[0-9]{11}";
        Pattern pattern = Pattern.compile(snilsTemplate);
        Matcher matcher = pattern.matcher(dto.getSnils());
        if (!matcher.find()){
            throw new DtoCompositionException("Неверный формат СНИЛС");
        }
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


    private void validateIfEntityAlreadyExist(String snils) throws EntityAlreadyExistException {
        Employee employeeFromDb = employeeRepository.findBySnils(snils);
        if (employeeFromDb != null){
            throw new EntityAlreadyExistException("Поверитель с СНИЛС " + snils + " уже существует");
        }
    }

    @Override
    public Page<EmployeeDto> findBySurname(String surname, Pageable pageable) {
            validateSearchString(surname);
            return employeeRepository.findBySurnameIgnoreCaseContaining(surname.trim(), pageable)
                    .map(EmployeeDtoMapper::mapToDto);
    }
    @Override
    public List<EmployeeDto>  findBySurname(String surname) {
            validateSearchString(surname);
            return employeeRepository.findBySurnameIgnoreCaseContaining(surname.trim()).stream()
                    .map(EmployeeDtoMapper::mapToDto).toList();
    }

    private void validateSearchString(String searchString) throws ParameterNotValidException {
        if (searchString == null || searchString.isEmpty()){
            throw  new ParameterNotValidException("Поле для поиска не может быть пустым");
        }
    }

    @Override
    public Page<EmployeeDto> findAll(Pageable pageable) {
        return employeeRepository.findAll(pageable).map(EmployeeDtoMapper ::mapToDto);
    }

    @Override
    public List<EmployeeDto> findAll() {
        return employeeRepository.findAll().stream().map(EmployeeDtoMapper ::mapToDto).toList();
    }

    @Override
    public void update(EmployeeDto employeeDto){
            checkEmployeeDtoComposition(employeeDto);
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
