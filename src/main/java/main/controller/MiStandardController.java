package main.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import main.config.AppConstants;
import main.dto.MiStandardDto;
import main.service.implementations.MiStandardService;
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
@RequestMapping("/standards/mis/")
public class MiStandardController {
    @Autowired
    private MiStandardService miStandardService;

    @GetMapping("/pages")
    public Page<MiStandardDto> getMiStandardPageableList(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNum,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "dir", defaultValue = AppConstants.DEFAULT_PAGE_SORT_DIR, required = false) String pageDir){
        Pageable pageable = PageRequest.of(pageNum, pageSize,
                Sort.by(Sort.Direction.valueOf(pageDir.toUpperCase()), "arshinNumber"));
        return miStandardService.findAll(pageable);
    }

    @GetMapping("/pages/search")
    public ResponseEntity<?> searchMiStandard(
            @RequestParam(value = "search") String searchString){
        Pageable pageable = PageRequest.of(0,10,
                Sort.by(Sort.Direction.ASC,"arshinNumber"));
        return miStandardService.findBySearchString(searchString,pageable);
    }

    @GetMapping
    public List<MiStandardDto> getMiStandardWthoutPageableList(){
        return miStandardService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMiStandard(@PathVariable("id") String id){
        return miStandardService.findById(Long.parseLong(id));
    }
    @PostMapping
    public ResponseEntity<?> addMiStandard(@RequestParam("miStandard") String miStandard,
                                           @RequestParam("descriptions") String[] descriptions,
                                           @RequestParam(name = "files", required = false) Optional<MultipartFile[]> filesOpt) throws IOException {
        ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MiStandardDto miStandardDto = mapper.readValue(miStandard,MiStandardDto.class);
        MultipartFile[] files = filesOpt.orElseGet(() -> new MultipartFile[0]);
        return miStandardService.save(miStandardDto, files, descriptions);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> editMiStandard(@RequestBody MiStandardDto miStandardDto){
        return miStandardService.update(miStandardDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMiStandard(@PathVariable("id") int id){
        return miStandardService.delete(id);
    }
}
