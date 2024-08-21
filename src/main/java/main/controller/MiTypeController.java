package main.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.config.AppConstants;
import main.dto.rest.MiTypeDto;
import main.dto.rest.MiTypeDetailsDto;
import main.service.ServiceMessage;
import main.service.interfaces.MiTypeDetailsService;
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
@Slf4j
@AllArgsConstructor
@RequestMapping("/mits")
public class MiTypeController {
    @Autowired
    private MiTypeDetailsService miTypeDetailsService;
    @Autowired
    private MiTypeService miTypeService;

    @GetMapping("/pages")
    public Page<MiTypeDto> getMiTypePageableList(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNum,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "dir", defaultValue = AppConstants.DEFAULT_PAGE_SORT_DIR, required = false) String pageDir,
            @RequestParam(value = "search", defaultValue = "", required = false) String searchString){
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.valueOf(pageDir.toUpperCase()), "number"));
        if (searchString.isEmpty() || searchString.equals("undefined")) {
            return miTypeService.findAll(pageable);
        }
        return miTypeService.findBySearchString(searchString,pageable);
    }


    @GetMapping("/search")
    public List<MiTypeDto> searchMiType(
            @RequestParam(value = "search") String searchString){
        return miTypeService.findBySearchString(searchString);
    }

    @GetMapping
    public List<MiTypeDto> getMiTypeWthoutPageableList(){
        return miTypeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMiType(@PathVariable("id") long id){
        MiTypeDetailsDto miTypeDetailsDto = miTypeDetailsService.findById(id);
        return ResponseEntity.ok(miTypeDetailsDto);
    }
    @GetMapping("/modifications/{id}")
    public List<String> getMiTypeModifications(@PathVariable("id") long id){
        return miTypeDetailsService.findModifications(id);
    }

    @GetMapping("/search/modifications/{id}")
    public List<String> getSearchMiTypeModifications(@PathVariable("id") long id,
                                                          @RequestParam("search") String searchString){
        return miTypeDetailsService.findModificationsByMiTypeDetailsIdAndSearchString(id, searchString);
    }

    @PostMapping
    public ResponseEntity<?> addMiType(@RequestParam("miTypeDetails") String miTypeDetails,
                                       @RequestParam(name = "files", required = false) Optional<MultipartFile[]> filesOpt,
                                       @RequestParam("descriptions") String[] descriptions) throws IOException {
        ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
        MiTypeDetailsDto miTypeDetailsDto = mapper.readValue(miTypeDetails, MiTypeDetailsDto.class);
        MultipartFile[] files = filesOpt.orElseGet(() -> new MultipartFile[0]);
        miTypeDetailsService.save(miTypeDetailsDto, files, descriptions);
        String okMessage = "Запись о типе СИ № " + miTypeDetailsDto.getMiType().getNumber() + " успешно добавлена";
        log.info(okMessage);
        return ResponseEntity.status(201).body(new ServiceMessage(okMessage));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editMiType(@RequestBody MiTypeDetailsDto miTypeDetailsDto){
        miTypeDetailsService.update(miTypeDetailsDto);
        String okMessage = "Cведения о типе СИ " + miTypeDetailsDto.getMiType().getNumber() + " обновлены";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteMiType(@PathVariable("id") long id) throws IOException {
        miTypeDetailsService.delete(id);
        String okMessage = "Запись о типе СИ успешно удалена";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }
}
