package main.service.interfaces;

import jakarta.validation.Valid;
import main.dto.rest.MiTypeDto;
import main.dto.rest.MiTypeFullDto;
import main.model.MiTypeInstruction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MiTypeService {
    ResponseEntity<?> save(@Valid MiTypeFullDto miTypeDto, MultipartFile[] files, String[] descriptions) throws IOException;
    ResponseEntity<?> findById(long id);
    MiTypeInstruction getInstructionById(long id);
    ResponseEntity<?> findByNumber(String number);
    ResponseEntity<?> findBySearchString(String searchString, Pageable pageable);
    ResponseEntity<?> findBySearchString(String searchString);
    ResponseEntity<?> findModifications(long miTypeId);
    ResponseEntity<?> findModificationsByMiTypeIdAndSearchString(long miTypeId, String searchString);
    Page<MiTypeDto> findAll(Pageable pageable);
    List<MiTypeDto> findAll();
    ResponseEntity<?> update(@Valid MiTypeFullDto miTypeDto);
    ResponseEntity<?>delete(long id) throws IOException;
}
