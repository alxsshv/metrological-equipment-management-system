package main.service.interfaces;

import main.dto.rest.MiStandardDto;
import main.model.MiStandard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MiStandardService {
    ResponseEntity<?> save(MiStandardDto miStandardDto, MultipartFile[] files, String[] descriptions) throws IOException;
    ResponseEntity<?> findById(long id);
    MiStandard getMiStandardById(long id);
    ResponseEntity<?> findByArshinNumber(String arshinNumber);
    ResponseEntity<?>  findBySearchString(String searchString);
    ResponseEntity<?>  findBySearchString(String searchString, Pageable pageable);
    Page<MiStandardDto> findAll(Pageable pageable);
    List<MiStandardDto> findAll();
    ResponseEntity<?> update(MiStandardDto miStandardDto);
    ResponseEntity<?>delete(long id);
}
