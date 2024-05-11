package main.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import main.config.AppConstants;
import main.dto.MiDto;
import main.dto.MiFullDto;
import main.service.implementations.MeasurementInstrumentService;
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
@RequestMapping("/mis")
public class MeasurementInstrumentController {
    @Autowired
    private MeasurementInstrumentService measurementInstrumentService;

    @GetMapping("/pages")
    public Page<MiDto> getMeasurementInstrumentPageableList(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNum,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "dir", defaultValue = AppConstants.DEFAULT_PAGE_SORT_DIR, required = false) String pageDir){
        Pageable pageable = PageRequest.of(pageNum, pageSize,
                Sort.by(Sort.Direction.valueOf(pageDir.toUpperCase()), "modification","serialNum"));
        return measurementInstrumentService.findAll(pageable);
    }

    @GetMapping("/pages/search")
    public ResponseEntity<?> searchMeasurementInstrumentWithPages(
            @RequestParam(value = "search", required = true) String searchString){
        Pageable pageable = PageRequest.of(0,10,
                Sort.by(Sort.Direction.ASC,"modification","serialNum"));
        return measurementInstrumentService.findBySearchString(searchString,pageable);
    }
    @GetMapping("/search")
    public ResponseEntity<?> searchMeasurementInstrumentWithoutPages(
            @RequestParam(value = "search") String searchString){
        System.out.println("поиск...");
        return measurementInstrumentService.findBySearchString(searchString);
    }

    @GetMapping
    public List<MiDto> getMeasurementInstrumentWthoutPageableList(){
        return measurementInstrumentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMeasurementInstrument(@PathVariable("id") String id){
        return measurementInstrumentService.findById(Integer.parseInt(id));
    }
    @PostMapping
    public ResponseEntity<?> addMeasurementInstrument(@RequestParam("instrument") String instrument,
                                                      @RequestParam(name = "files", required = false) Optional<MultipartFile[]> filesOpt,
                                                      @RequestParam("descriptions") String[] descriptions) throws IOException {
        ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MiFullDto miFullDto = mapper.readValue(instrument, MiFullDto.class);
        MultipartFile[] files = filesOpt.orElseGet(() -> new MultipartFile[0]);
        return measurementInstrumentService.save(miFullDto,files,descriptions);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editMeasurementInstrument(@RequestBody MiFullDto instrumentDto){
        return measurementInstrumentService.update(instrumentDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteMeasurementInstrument(@PathVariable("id") int id){
        return measurementInstrumentService.delete(id);
    }
}
