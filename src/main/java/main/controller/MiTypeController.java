package main.controller;

import lombok.AllArgsConstructor;
import main.config.AppConstants;
import main.dto.MiTypeDto;
import main.dto.MiTypeFullDto;
import main.service.implementations.MiTypeService;
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
@RequestMapping("/mit")
public class MiTypeController {
    @Autowired
    private MiTypeService miTypeService;

    @GetMapping("/pages")
    public Page<MiTypeDto> getMiTypePageableList(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNum,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "dir", defaultValue = AppConstants.DEFAULT_PAGE_SORT_DIR, required = false) String pageDir){
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.valueOf(pageDir.toUpperCase()), "number"));
        return miTypeService.findAll(pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchMiType(
            @RequestParam(value = "search", required = true) String searchString){
        Pageable pageable = PageRequest.of(0,10,Sort.by(Sort.Direction.ASC,"number"));
        return miTypeService.findBySearchString(searchString,pageable);
    }

    @GetMapping
    public List<MiTypeDto> getMiTypeWthoutPageableList(){
        return miTypeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMiType(@PathVariable("id") String id){
        return miTypeService.findById(Integer.parseInt(id));
    }
    @GetMapping("/modifications/{id}")
    public ResponseEntity<?> getMiTypeModifications(@PathVariable("id") String id){
        return miTypeService.findModifications(Integer.parseInt(id));
    }
    @PostMapping
    public ResponseEntity<?> addMiType(@RequestBody MiTypeFullDto miTypeDto) {
        return miTypeService.save(miTypeDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editMiType(@RequestBody MiTypeFullDto miTypeDto){
        return miTypeService.update(miTypeDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteMiType(@PathVariable("id") int id){
        return miTypeService.delete(id);
    }
}
