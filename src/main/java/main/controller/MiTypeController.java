package main.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import main.config.AppConstants;
import main.dto.rest.MiTypeDto;
import main.dto.rest.MiTypeFullDto;
import main.service.interfaces.MiTypeService;
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
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/mits")
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

    @GetMapping("/pages/search")
    public ResponseEntity<?> searchMiTypeWithPages(
            @RequestParam(value = "search") String searchString){
        Pageable pageable = PageRequest.of(0,10,Sort.by(Sort.Direction.ASC,"number"));
        return miTypeService.findBySearchString(searchString,pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchMiType(
            @RequestParam(value = "search") String searchString){
        return miTypeService.findBySearchString(searchString);
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
                                       @RequestParam(name = "files", required = false) Optional<MultipartFile[]> filesOpt,
                                       @RequestParam("descriptions") String[] descriptions) throws IOException {
        ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
        MiTypeFullDto miTypeFullDto = mapper.readValue(miType, MiTypeFullDto.class);
        MultipartFile[] files = filesOpt.orElseGet(() -> new MultipartFile[0]);
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
