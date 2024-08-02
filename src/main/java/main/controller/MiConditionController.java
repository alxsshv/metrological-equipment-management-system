package main.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.config.AppConstants;
import main.dto.rest.MiConditionDto;
import main.service.ServiceMessage;
import main.service.interfaces.MiConditionService;
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
@RequestMapping("/mi_conditions")
public class MiConditionController {
    @Autowired
    private MiConditionService miConditionService;

    @GetMapping("/pages")
    public Page<MiConditionDto> getMiConditionPageableList(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNum,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "dir", defaultValue = AppConstants.DEFAULT_PAGE_SORT_DIR, required = false) String pageDir,
            @RequestParam(value = "search", defaultValue = "") String searchString){
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.valueOf(pageDir.toUpperCase()), "title"));
        if(searchString.isEmpty() || searchString.equals("undefined")) {
            return miConditionService.findAll(pageable);
        }
        return miConditionService.findByTitle(searchString,pageable);
    }

    @GetMapping("/search")
    public List<MiConditionDto>  searchMiCondition(
            @RequestParam(value = "search") String title){
        return miConditionService.findByTitle(title);
    }

    @GetMapping
    public List<MiConditionDto> getMiConditionListWithoutPageable(){
        return miConditionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMiCondition(@PathVariable("id") long id){
        MiConditionDto dto = miConditionService.findById(id);
        return ResponseEntity.ok(dto);
    }
    @PostMapping
    public ResponseEntity<?> addMiCondition (@RequestBody MiConditionDto miConditionDto){
        miConditionService.save(miConditionDto);
        String okMessage = "Состояние средства измерений \"" + miConditionDto.getTitle() + "\" успешно добавлено";
        log.info(okMessage);
        return ResponseEntity.status(201).body(new ServiceMessage(okMessage));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editMiCondition(@RequestBody MiConditionDto miConditionDto){
        miConditionService.update(miConditionDto);
        String okMessage = "Сотояние средства измерений \"" + miConditionDto.getTitle() + "\" обновлено";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteMiCondition(@PathVariable("id") long id){
        miConditionService.delete(id);
        String okMessage ="Запись о состоянии средства измерений № " + id + " успешно удалена";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }
}
