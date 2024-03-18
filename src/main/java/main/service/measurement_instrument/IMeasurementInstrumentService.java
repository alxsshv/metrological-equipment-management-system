package main.service.measurement_instrument;

import main.dto.MeasurementInstrumentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IMeasurementInstrumentService {
    ResponseEntity<?> save(MeasurementInstrumentDto instrumentDto);
    ResponseEntity<?> update(MeasurementInstrumentDto instrumentDto);
    ResponseEntity<?>delete(int id);
    ResponseEntity<?> findById(int id);
    ResponseEntity<?> findBySearchString(String searchString, Pageable pageable);
    Page<MeasurementInstrumentDto> findAll(Pageable pageable);
    List<MeasurementInstrumentDto> findAll();
}
