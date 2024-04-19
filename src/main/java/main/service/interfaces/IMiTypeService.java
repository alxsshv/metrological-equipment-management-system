package main.service.interfaces;

import main.dto.MiTypeDto;
import main.dto.MiTypeFullDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IMiTypeService {
    ResponseEntity<?> save(MiTypeFullDto miTypeDto, MultipartFile[] files,String[] descriptions) throws IOException;
    ResponseEntity<?> update(MiTypeFullDto miTypeDto);
    ResponseEntity<?>delete(long id) throws IOException;
    ResponseEntity<?> findById(long id);
    ResponseEntity<?> findByNumber(String number);
    ResponseEntity<?> findBySearchString(String searchString, Pageable pageable);
    ResponseEntity<?> findModifications(long miTypeId);
    Page<MiTypeDto> findAll(Pageable pageable);
    List<MiTypeDto> findAll();
}
