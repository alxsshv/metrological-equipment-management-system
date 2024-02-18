package main.controller;

import lombok.AllArgsConstructor;
import main.dto.EmployeeDto;
import main.service.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<EmployeeDto> geEmployeeList(){
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable("id") String id){
        return employeeService.findById(Integer.parseInt(id));
    }
    @PostMapping
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeDto employeeDto){
        return employeeService.save(employeeDto);
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> editEmployee(@RequestBody EmployeeDto employeeDto){
        return employeeService.update(employeeDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") int id){
        return employeeService.delete(id);
    }
}
