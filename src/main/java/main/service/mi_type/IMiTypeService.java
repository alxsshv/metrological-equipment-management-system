package main.service.mi_type;

import main.dto.MiTypeDto;
import main.dto.MiTypeFullDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IMiTypeService {
    ResponseEntity<?> save(MiTypeFullDto miTypeDto);
    ResponseEntity<?> update(MiTypeFullDto miTypeDto);
    ResponseEntity<?>delete(int id);
    ResponseEntity<?> findById(int id);
    ResponseEntity<?> findBySearchString(String searchString, Pageable pageable);
    Page<MiTypeDto> findAll(Pageable pageable);
    List<MiTypeDto> findAll();
}
