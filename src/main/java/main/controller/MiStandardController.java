package main.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.config.AppConstants;
import main.dto.rest.MiStandardDto;
import main.service.ServiceMessage;
import main.service.interfaces.MiDetailsService;
import main.service.interfaces.MiStandardService;
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

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/standards/mis/")
public class MiStandardController {
    @Autowired
    private MiStandardService miStandardService;
    @Autowired
    private MiDetailsService miDetailsService;


    @GetMapping("/pages")
    public Page<MiStandardDto> getMiStandardPageableList(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNum,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "dir", defaultValue = AppConstants.DEFAULT_PAGE_SORT_DIR, required = false) String pageDir,
            @RequestParam(value = "search", defaultValue = "", required = false) String searchString){
        Pageable pageable = PageRequest.of(pageNum, pageSize,
                Sort.by(Sort.Direction.valueOf(pageDir.toUpperCase()), "arshinNumber"));
        if (searchString.isEmpty() || searchString.equals("undefined")) {
            return miStandardService.findAll(pageable);
        }
        return miStandardService.findBySearchString(searchString, pageable);
    }


    @GetMapping("/search")
    public ResponseEntity<?> searchMiStandard(
            @RequestParam(value = "search") String searchString){
        List<MiStandardDto> dtos = miStandardService.findBySearchString(searchString);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping
    public List<MiStandardDto> getMiStandardWthoutPageableList(){
        return miStandardService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMiStandard(@PathVariable("id") String id){
        MiStandardDto miStandardDto = miStandardService.findById(Long.parseLong(id));
        return ResponseEntity.ok(miStandardDto);
    }
    @PostMapping
    public ResponseEntity<?> addMiStandard(@RequestParam("miStandard") String miStandard,
                                           @RequestParam("descriptions") String[] descriptions,
                                           @RequestParam(name = "files", required = false) Optional<MultipartFile[]> filesOpt) throws IOException {
        ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MiStandardDto miStandardDto = mapper.readValue(miStandard,MiStandardDto.class);
        MultipartFile[] files = filesOpt.orElseGet(() -> new MultipartFile[0]);
        miStandardService.save(miStandardDto, files, descriptions);
        String okMessage = "Запись об эталоне № " + miStandardDto.getArshinNumber() + " успешно добавлена";
        log.info(okMessage);
        return ResponseEntity.status(201).body(new ServiceMessage(okMessage));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> editMiStandard(@RequestBody MiStandardDto miStandardDto){
        miStandardService.update(miStandardDto);
        String okMessage = "Cведения об эталоне № " + miStandardDto.getArshinNumber() + " обновлены";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMiStandard(@PathVariable("id") long id){
        miStandardService.delete(id);
        String okMessage ="Запись об эталоне успешно удалена";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }
}
