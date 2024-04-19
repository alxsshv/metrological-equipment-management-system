package main.service.interfaces;

import main.dto.MiStandardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IMiStandardService {
    ResponseEntity<?> save(MiStandardDto miStandardDto);
    ResponseEntity<?> update(MiStandardDto miStandardDto);
    ResponseEntity<?>delete(long id);
    ResponseEntity<?> findById(long id);
    ResponseEntity<?> findByArshinNumber(String arshinNumber);
    ResponseEntity<?> findBySearchString(String searchString, Pageable pageable);
    Page<MiStandardDto> findAll(Pageable pageable);
    List<MiStandardDto> findAll();
}
