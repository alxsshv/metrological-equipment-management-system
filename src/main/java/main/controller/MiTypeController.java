package main.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ResponseEntity<?> addMiType(@RequestParam("miType") String miType,
                                       @RequestParam("files") MultipartFile[] files,
                                       @RequestParam("descriptions") String[] descriptions) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        MiTypeFullDto miTypeFullDto = mapper.readValue(miType, MiTypeFullDto.class);
        System.out.println(miTypeFullDto);
        System.out.println(files.length);
        System.out.println(descriptions.length);
        return miTypeService.save(miTypeFullDto, files, descriptions);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editMiType(@RequestBody MiTypeFullDto miTypeDto){
        return miTypeService.update(miTypeDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteMiType(@PathVariable("id") int id) throws IOException {
        return miTypeService.delete(id);
    }
}
