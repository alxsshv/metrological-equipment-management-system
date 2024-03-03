package main.service.employee;

import lombok.RequiredArgsConstructor;
import main.dto.EmployeeDto;
import main.dto.mappers.EmployeeDtoMapper;
import main.model.Employee;
import main.repository.EmployeeRepository;
import main.service.ServiceMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {
    private final EmployeeRepository employeeRepository;

    @Override
    public ResponseEntity<?> save(EmployeeDto employeeDto) {
        String errorMessage = checkEmployeeDtoComposition(employeeDto);
        Employee employeeFromDb = employeeRepository.findBySnils(employeeDto.getSnils());
        if (employeeFromDb != null){
            errorMessage = "Поверитель с СНИЛС " + employeeDto.getSnils() + " уже существует";
        }
        if (!errorMessage.isEmpty()) {
            System.out.println(errorMessage);
            return ResponseEntity.status(422).body(
                    new ServiceMessage(errorMessage));
        }
        employeeRepository.save(EmployeeDtoMapper.mapToEntity(employeeDto));
        String okMessage = "Поверитель " + employeeDto.getName() + " " + employeeDto.getSurname() + " успешно добавлен";
        System.out.println(okMessage);
        return ResponseEntity.ok(
                new ServiceMessage(okMessage));
    }
    private String checkEmployeeDtoComposition(EmployeeDto dto){
        if (dto.getSurname() == null || dto.getSurname().isEmpty()){
            return "Пожалуйста заполните фамилию поверителя";
        }
        if (dto.getName() == null || dto.getName().isEmpty()){
            return "Пожалуйста заполните имя поверителя";
        }
        if (dto.getPatronymic() == null || dto.getPatronymic().isEmpty()){
            return "Пожалуйста заполните отчество поверителя";
        }
        String snilsTemplate = "/d{11}";
        Pattern pattern = Pattern.compile(snilsTemplate);
        Matcher matcher = pattern.matcher(dto.getSnils());
        if (matcher.find()){
            return "Неверный формат СНИЛС";
        }
        return "";
    }

@Override
    public ResponseEntity<?> update(EmployeeDto employeeDto){
        Optional<Employee> userOpt = employeeRepository.findById(employeeDto.getId());
        if (userOpt.isEmpty()){
            String errorMessage = "Поверитель " + employeeDto.getSurname() +
                    " " + employeeDto.getName() +  " не найден";
            return ResponseEntity.status(404).body(new ServiceMessage(errorMessage));
        }
        Employee updatingEmployeeData = EmployeeDtoMapper.mapToEntity(employeeDto);
        Employee employee = userOpt.get();
        employee. updateFrom(updatingEmployeeData);
        employeeRepository.save(employee);
        String okMessage ="Cведения о поверителе " + employee.getName() + " "
                + employee.getSurname() + " обновлены";
        System.out.println(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }
@Override
    public ResponseEntity<?>delete(int id){
        Optional<Employee> userOpt = employeeRepository.findById(id);
        if (userOpt.isEmpty()){
            String errorMessage = "Данные по id = "+ id +" не найдены";
            return ResponseEntity.status(404).body(new ServiceMessage(errorMessage));
        }
        employeeRepository.delete(userOpt.get());
        String okMessage ="Запись о поверителе успешно удалена";
        System.out.println(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }

    @Override
    public ResponseEntity<?> findById(int id) {
        Optional<Employee> employeeOpt = employeeRepository.findById(id);
        if (employeeOpt.isPresent()) {
            EmployeeDto employeeDto = EmployeeDtoMapper.mapToDto(employeeOpt.get());
            return ResponseEntity.ok(employeeDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public Page<EmployeeDto> findBySurname(String surname, Pageable pageable) {
        return employeeRepository.findBySurname(surname.trim(),pageable).map(EmployeeDtoMapper::mapToDto);
    }

    @Override
    public Page<EmployeeDto> findAll(Pageable pageable) {
        return employeeRepository.findAll(pageable).map(EmployeeDtoMapper ::mapToDto);
    }

    @Override
    public List<EmployeeDto> findAll() {
        return employeeRepository.findAll().stream().map(EmployeeDtoMapper ::mapToDto).toList();
    }



}
