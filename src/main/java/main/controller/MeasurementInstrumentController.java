package main.controller;

import lombok.AllArgsConstructor;
import main.config.AppConstants;
import main.dto.MiDto;
import main.dto.MiFullDto;
import main.service.measurement_instrument.MeasurementInstrumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/mi")
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
    public ResponseEntity<?> searchMeasurementInstrument(
            @RequestParam(value = "search", required = true) String searchString){
        Pageable pageable = PageRequest.of(0,10,
                Sort.by(Sort.Direction.ASC,"modification","serialNum"));
        return measurementInstrumentService.findBySearchString(searchString,pageable);
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
    public ResponseEntity<?> addMeasurementInstrument(@RequestBody MiFullDto instrumentDto) throws IOException {
        return measurementInstrumentService.save(instrumentDto);
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> editMeasurementInstrument(@RequestBody MiFullDto instrumentDto){
        return measurementInstrumentService.update(instrumentDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteMeasurementInstrument(@PathVariable("id") int id){
        return measurementInstrumentService.delete(id);
    }
}
