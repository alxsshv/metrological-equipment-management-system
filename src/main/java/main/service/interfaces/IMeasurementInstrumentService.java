package main.service.interfaces;

import main.dto.MiDto;
import main.dto.MiFullDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IMeasurementInstrumentService {
    ResponseEntity<?> save(MiFullDto instrumentDto, MultipartFile[] files, String[] descriptions) throws IOException;
    ResponseEntity<?> update(MiFullDto instrumentDto);
    ResponseEntity<?>delete(long id);
    ResponseEntity<?> findById(long id);
    ResponseEntity<?> findBySearchString(String searchString, Pageable pageable);
    Page<MiDto> findAll(Pageable pageable);
    List<MiDto> findAll();
}
