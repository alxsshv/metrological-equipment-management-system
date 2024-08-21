package main.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.config.AppConstants;
import main.dto.rest.MiStatusDto;
import main.service.ServiceMessage;
import main.service.interfaces.MiStatusService;
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
@RequestMapping("/mi_statuses")
public class MiStatusController {
    @Autowired
    private MiStatusService miStatusService;

    @GetMapping("/pages")
    public Page<MiStatusDto> getMiStatusPageableList(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNum,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "dir", defaultValue = AppConstants.DEFAULT_PAGE_SORT_DIR, required = false) String pageDir,
            @RequestParam(value = "search", defaultValue = "") String searchString){
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.valueOf(pageDir.toUpperCase()), "status"));
        if(searchString.isEmpty() || searchString.equals("undefined")) {
            return miStatusService.findAll(pageable);
        }
        return miStatusService.findByStatus(searchString,pageable);
    }

    @GetMapping("/search")
    public List<MiStatusDto>  searchMiStatus(
            @RequestParam(value = "search") String status){
        return miStatusService.findByStatus(status);
    }

    @GetMapping
    public List<MiStatusDto> getMiStatusListWithoutPageable(){
        return miStatusService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMiStatus(@PathVariable("id") long id){
        MiStatusDto dto = miStatusService.findById(id);
        return ResponseEntity.ok(dto);
    }
    @PostMapping
    public ResponseEntity<?> addMiStatus (@RequestBody MiStatusDto miStatusDto){
        miStatusService.save(miStatusDto);
        String okMessage = "Статус средства измерений \"" + miStatusDto.getStatus() + "\" успешно добавлен";
        log.info(okMessage);
        return ResponseEntity.status(201).body(new ServiceMessage(okMessage));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editMiStatus(@RequestBody MiStatusDto miStatusDto){
        miStatusService.update(miStatusDto);
        String okMessage = "Статус средства измерений \"" + miStatusDto.getStatus() + "\" обновлен";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteMiStatus(@PathVariable("id") long id){
        miStatusService.delete(id);
        String okMessage ="Запись о статусе средства измерений успешно удалена";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }
}
