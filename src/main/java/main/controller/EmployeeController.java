package main.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.config.AppConstants;
import main.dto.rest.EmployeeDto;
import main.service.ServiceMessage;
import main.service.interfaces.EmployeeServiceFacade;
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
@Slf4j
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeServiceFacade employeeServiceFacade;

    @GetMapping("/pages")
    public Page<EmployeeDto> getEmployeePageableList(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNum,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "dir", defaultValue = AppConstants.DEFAULT_PAGE_SORT_DIR, required = false) String pageDir,
            @RequestParam(value = "search", defaultValue = "") String searchString){
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.valueOf(pageDir.toUpperCase()), "user.surname"));
        if(searchString.isEmpty() || searchString.equals("undefined")) {
            return employeeServiceFacade.findAll(pageable);
        }
        return employeeServiceFacade.findBySurname(searchString, pageable);
    }

    @GetMapping("/search")
    public List<EmployeeDto>  searchEmployee(
            @RequestParam(value = "search") String surname){
        return employeeServiceFacade.findAll();
    }

    @GetMapping
    public List<EmployeeDto> getEmployeeWithoutPageableList(){
        return employeeServiceFacade.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable("id") String id){
        EmployeeDto dto = employeeServiceFacade.findById(Long.parseLong(id));
        return ResponseEntity.ok(dto);
    }
    @PostMapping
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeDto employeeDto){
        employeeServiceFacade.save(employeeDto);
        String okMessage = "Поверитель " + employeeDto.getUserDto().getName() + " " + employeeDto.getUserDto().getSurname() + " успешно добавлен";
        log.info(okMessage);
        return ResponseEntity.status(201).body(new ServiceMessage(okMessage));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editEmployee(@RequestBody EmployeeDto employeeDto){
        employeeServiceFacade.update(employeeDto);
        String okMessage = "Сведения о поверителе " + employeeDto.getUserDto().getName() + " "
                + employeeDto.getUserDto().getSurname() + " обновлены";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") long id){
        employeeServiceFacade.delete(id);
        String okMessage ="Запись о поверителе успешно удалена";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }
}
