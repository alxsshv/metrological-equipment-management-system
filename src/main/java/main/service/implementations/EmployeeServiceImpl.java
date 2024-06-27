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
import main.service.ServiceMessage;
import main.service.interfaces.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> save(EmployeeDto employeeDto) {
        try {
            checkEmployeeDtoComposition(employeeDto);
            validateIfEntityAlreadyExist(employeeDto.getSnils());
            employeeRepository.save(EmployeeDtoMapper.mapToEntity(employeeDto));
            String okMessage = "Поверитель " + employeeDto.getName() + " " + employeeDto.getSurname() + " успешно добавлен";
            log.info(okMessage);
            return ResponseEntity.status(201).body(new ServiceMessage(okMessage));
        } catch (EntityAlreadyExistException | DtoCompositionException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(400).body(
                    new ServiceMessage(ex.getMessage()));
        }
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
    public ResponseEntity<?> findById(long id) {
        try {
            Employee employee = getEmployeeById(id);
            EmployeeDto employeeDto = EmployeeDtoMapper.mapToDto(employee);
            return ResponseEntity.ok(employeeDto);
        } catch (EntityNotFoundException ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(400).body(ex.getMessage());
        }
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
    public ResponseEntity<?> findBySurname(String surname, Pageable pageable) {
        try {
            validateSearchString(surname);
            Page<EmployeeDto> page = employeeRepository.findBySurnameContaining(surname.trim(), pageable)
                    .map(EmployeeDtoMapper::mapToDto);
            return ResponseEntity.ok(page);
        } catch (ParameterNotValidException ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(400).body(new ServiceMessage(ex.getMessage()));
        }
    }
    @Override
    public ResponseEntity<?> findBySurname(String surname) {
        try {
            validateSearchString(surname);
            List<EmployeeDto> list = employeeRepository.findBySurnameContaining(surname.trim()).stream()
                    .map(EmployeeDtoMapper::mapToDto).toList();
            return ResponseEntity.ok(list);
        } catch (ParameterNotValidException ex){
            log.info(ex.getMessage());
            return ResponseEntity.status(400).body(new ServiceMessage(ex.getMessage()));

        }
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
    public ResponseEntity<?> update(EmployeeDto employeeDto){
        try {
            checkEmployeeDtoComposition(employeeDto);
            Employee employee = getEmployeeById(employeeDto.getId());
            Employee updatingEmployeeData = EmployeeDtoMapper.mapToEntity(employeeDto);
            employee.updateFrom(updatingEmployeeData);
            employeeRepository.save(employee);
            String okMessage = "Сведения о поверителе " + employee.getName() + " "
                    + employee.getSurname() + " обновлены";
            log.info(okMessage);
            return ResponseEntity.ok(new ServiceMessage(okMessage));
        } catch (DtoCompositionException | EntityNotFoundException ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(400).body(new ServiceMessage(ex.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?>delete(long id){
        employeeRepository.deleteById(id);
        String okMessage ="Запись о поверителе № " + id + " успешно удалена";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }

}
