package main.service.interfaces;

import jakarta.validation.Valid;
import main.dto.rest.MiTypeDetailsDto;
import main.model.MiTypeDetails;
import main.service.validators.MiTypeAlreadyExist;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MiTypeDetailsService {
    void save(@MiTypeAlreadyExist @Valid MiTypeDetailsDto miTypeDetailsDto, MultipartFile[] files, String[] descriptions) throws IOException;
    MiTypeDetailsDto findById(long id);
    MiTypeDetails getById(long id);
    List<String> findModifications(long miTypeId);
    List<String> findModificationsByMiTypeDetailsIdAndSearchString(long miTypeId, String searchString);
    void update(@Valid MiTypeDetailsDto miTypeDetailsDto);
    void delete(long id) throws IOException;
}
