package main.service.interfaces;

import main.dto.rest.MiDto;
import main.dto.rest.MiFullDto;
import main.model.MeasurementInstrument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MeasurementInstrumentService {
    ResponseEntity<?> save(MiFullDto instrumentDto, MultipartFile[] files, String[] descriptions) throws IOException;
    ResponseEntity<?> findById(long id);
    MeasurementInstrument getMiById(long id);
    ResponseEntity<?> findBySearchString(String searchString, Pageable pageable);
    ResponseEntity<?> findBySearchString(String searchString);
    Page<MiDto> findAll(Pageable pageable);
    List<MiDto> findAll();
    ResponseEntity<?> update(MiFullDto instrumentDto);
    ResponseEntity<?>delete(long id);
}
