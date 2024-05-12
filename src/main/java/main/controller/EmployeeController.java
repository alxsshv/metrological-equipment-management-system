package main.controller;

import lombok.AllArgsConstructor;
import main.config.AppConstants;
import main.dto.EmployeeDto;
import main.service.implementations.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/pages")
    public Page<EmployeeDto> getEmployeePageableList(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNum,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "dir", defaultValue = AppConstants.DEFAULT_PAGE_SORT_DIR, required = false) String pageDir){
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.valueOf(pageDir.toUpperCase()), "surname"));
        return employeeService.findAll(pageable);
    }

    @GetMapping("/pages/search")
    public ResponseEntity<?>  searchEmployeeWithPages(
            @RequestParam(value = "search", required = true) String surname){
        Pageable pageable = PageRequest.of(0,10,Sort.by(Sort.Direction.ASC,"surname"));
        return employeeService.findBySurname(surname,pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<?>  searchEmployee(
            @RequestParam(value = "search") String surname){
        return employeeService.findBySurname(surname);
    }

    @GetMapping
    public List<EmployeeDto> geEmployeeWithoutPageableList(){
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

    @PutMapping("{id}")
    public ResponseEntity<?> editEmployee(@RequestBody EmployeeDto employeeDto){
        return employeeService.update(employeeDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") int id){
        return employeeService.delete(id);
    }
}
