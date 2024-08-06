package main.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.config.AppConstants;
import main.dto.rest.MeasCategoryDto;
import main.service.ServiceMessage;
import main.service.interfaces.MeasCategoryService;
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
@RequestMapping("/measurement-categories")
public class MeasCategoryController {
    @Autowired
    private MeasCategoryService measCategoryService;

    @GetMapping("/pages")
    public Page<MeasCategoryDto> getMeasCategoryPageableList(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNum,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "dir", defaultValue = AppConstants.DEFAULT_PAGE_SORT_DIR, required = false) String pageDir,
            @RequestParam(value = "search", defaultValue = "") String searchString){
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.valueOf(pageDir.toUpperCase()), "title"));
        if(searchString.isEmpty() || searchString.equals("undefined")) {
            return measCategoryService.findAll(pageable);
        }
        return measCategoryService.findByTitle(searchString,pageable);
    }

    @GetMapping("/search")
    public List<MeasCategoryDto>  searchMeasCategory(
            @RequestParam(value = "search") String title){
        return measCategoryService.findByTitle(title);
    }

    @GetMapping
    public List<MeasCategoryDto> getMeasCategoryListWithoutPageable(){
        return measCategoryService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMeasCategory(@PathVariable("id") long id){
        MeasCategoryDto dto = measCategoryService.findById(id);
        return ResponseEntity.ok(dto);
    }
    @PostMapping
    public ResponseEntity<?> addMeasCategory (@RequestBody MeasCategoryDto measCategoryDto){
        measCategoryService.save(measCategoryDto);
        String okMessage = "Статус средства измерений \"" + measCategoryDto.getTitle() + "\" успешно добавлен";
        log.info(okMessage);
        return ResponseEntity.status(201).body(new ServiceMessage(okMessage));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editMeasCategory(@RequestBody MeasCategoryDto measCategoryDto){
        measCategoryService.update(measCategoryDto);
        String okMessage = "Статус средства измерений \"" + measCategoryDto.getTitle() + "\" обновлен";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteMeasCategory(@PathVariable("id") long id){
        measCategoryService.delete(id);
        String okMessage ="Запись о статусе средства измерений № " + id + " успешно удалена";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }
}
