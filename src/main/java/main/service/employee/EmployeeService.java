package main.service.employee;

import main.dto.EmployeeDto;
import main.dto.mappers.EmployeeDtoMapper;
import main.model.Employee;
import main.repository.EmployeeRepository;
import main.service.ServiceMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService implements IEmployeeService {
    private final EmployeeRepository employeeRepository;
    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @Override
    public ResponseEntity<?> save(EmployeeDto employeeDto) {

        Employee employeeFromDb = employeeRepository.findBySnils(employeeDto.getSnils());
        if (employeeFromDb != null){
            String errorMessage = "Поверитель с СНИЛС " + employeeDto.getSnils() + " уже существует";
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

@Override
    public ResponseEntity<?> update(EmployeeDto employeeDto){
        Optional<Employee> userOpt = employeeRepository.findById(employeeDto.getId());
        if (userOpt.isEmpty()){
            String errorMessage = "Поверитель " + employeeDto.getSurname()+ " " + employeeDto.getName() +  " не найден";
            return ResponseEntity.status(404).body(new ServiceMessage(errorMessage));
        }
        Employee updatingEmployeeData = EmployeeDtoMapper.mapToEntity(employeeDto);
        Employee employee = userOpt.get();
        employee. updateFrom(updatingEmployeeData);
        employeeRepository.save(employee);
        String okMessage ="Cведения о поверителе " + employee.getEmployeeName().getName() + " "
                + employee.getEmployeeName().getSurname() + " обновлены";
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
    public List<EmployeeDto> findAll() {
        return employeeRepository.findAll().stream().map(EmployeeDtoMapper::mapToDto).toList();
    }



}
