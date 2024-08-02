package main.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.config.AppConstants;
import main.dto.rest.MiDetailsDto;
import main.dto.rest.MiDto;
import main.service.ServiceMessage;
import main.service.interfaces.MeasurementInstrumentService;
import main.service.interfaces.MiDetailsService;
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
@Slf4j
public class MiController {
    @Autowired
    private MeasurementInstrumentService measurementInstrumentService;
    @Autowired
    private MiDetailsService miDetailsService;

    @GetMapping("/pages")
    public Page<MiDto> getMeasurementInstrumentPageableList(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNum,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "dir", defaultValue = AppConstants.DEFAULT_PAGE_SORT_DIR, required = false) String pageDir,
            @RequestParam(value = "search", defaultValue = "", required = false) String searchString){
        Pageable pageable = PageRequest.of(pageNum, pageSize,
                Sort.by(Sort.Direction.valueOf(pageDir.toUpperCase()), "modification","serialNum"));
        if (searchString.isEmpty() || searchString.equals("undefined")) {
            return measurementInstrumentService.findAll(pageable);
        }
        return measurementInstrumentService.findBySearchString(searchString,pageable);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchMeasurementInstrumentWithoutPages(
            @RequestParam(value = "search") String searchString){
        List<MiDto> instruments = measurementInstrumentService.findBySearchString(searchString);
        return ResponseEntity.ok(instruments);
    }

    @GetMapping
    public List<MiDto> getMeasurementInstrumentWthoutPageableList(){
        return measurementInstrumentService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMeasurementInstrument(@PathVariable("id") long id){
        MiDetailsDto dto = miDetailsService.findById(id);
        return ResponseEntity.ok(dto);
    }
    @PostMapping
    public ResponseEntity<?> addMeasurementInstrument(@RequestParam("instrument") String instrument,
                                                      @RequestParam(name = "files", required = false) Optional<MultipartFile[]> filesOpt,
                                                      @RequestParam("descriptions") String[] descriptions) throws IOException {
        ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MiDetailsDto miDetailsDto = mapper.readValue(instrument, MiDetailsDto.class);
        MultipartFile[] files = filesOpt.orElseGet(() -> new MultipartFile[0]);
        miDetailsService.save(miDetailsDto,files,descriptions);
        String okMessage = "Запись о средстве измерений " + miDetailsDto.getMiFullDto().getModification() + " зав. № " +
                miDetailsDto.getMiFullDto().getSerialNum() + " успешно добавлена";
        log.info(okMessage);
        return ResponseEntity.status(201).body(new ServiceMessage(okMessage));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> editMeasurementInstrument(@RequestBody MiDetailsDto miDetailsDto){
        miDetailsService.update(miDetailsDto);
        String okMessage = "Cведения о средстве измерений успешно обновлены";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteMeasurementInstrument(@PathVariable("id") long id) {
        miDetailsService.delete(id);
        String okMessage = "Запись о средстве измерений № " + id + " успешно удалена";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }
}
