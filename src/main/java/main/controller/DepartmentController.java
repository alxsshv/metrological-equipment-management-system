package main.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.config.AppConstants;
import main.dto.rest.DepartmentDto;
import main.service.ServiceMessage;
import main.service.interfaces.DepartmentService;
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
@RequestMapping("/departments")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/pages")
    public Page<DepartmentDto> getDepartmentPageableList(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNum,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "dir", defaultValue = AppConstants.DEFAULT_PAGE_SORT_DIR, required = false) String pageDir,
            @RequestParam(value = "search", defaultValue = "") String searchString){
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.valueOf(pageDir.toUpperCase()), "notation"));
        if(searchString.isEmpty() || searchString.equals("undefined")) {
            return departmentService.findAll(pageable);
        }
        return departmentService.findByNotation(searchString,pageable);
    }

    @GetMapping("/search")
    public List<DepartmentDto>  searchDepartment(
            @RequestParam(value = "search") String notation){
        return departmentService.findByNotation(notation);
    }

    @GetMapping
    public List<DepartmentDto> getDepartmentWithoutPageableList(){
        return departmentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartment(@PathVariable("id") long id){
        DepartmentDto dto = departmentService.findById(id);
        return ResponseEntity.ok(dto);
    }
    @PostMapping
    public ResponseEntity<?> addDepartment (@RequestBody DepartmentDto departmentDto){
        departmentService.save(departmentDto);
        String okMessage = "Подразделение " + departmentDto.getNotation() + " успешно добавлен";
        log.info(okMessage);
        return ResponseEntity.status(201).body(new ServiceMessage(okMessage));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editDepartment(@RequestBody DepartmentDto departmentDto){
        departmentService.update(departmentDto);
        String okMessage = "Сведения о подразделении " + departmentDto.getNotation() + " обновлены";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable("id") long id){
        departmentService.delete(id);
        String okMessage ="Запись о подразделении успешно удалена";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }
}
