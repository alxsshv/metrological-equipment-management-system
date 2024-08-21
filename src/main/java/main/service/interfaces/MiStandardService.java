package main.service.interfaces;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import main.dto.rest.MiStandardDto;
import main.model.MiStandard;
import main.service.validators.MiStandardAlreadyExist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MiStandardService {
    void save(@MiStandardAlreadyExist @Valid MiStandardDto miStandardDto, MultipartFile[] files, String[] descriptions) throws IOException;
    MiStandard getMiStandardById(long id);
    MiStandardDto findById(long id);
    List<MiStandardDto> findBySearchString(@NotBlank(message = "Поле для поиска не может быть пустым") String searchString);
    Page<MiStandardDto> findBySearchString(@NotBlank(message = "Поле для поиска не может быть пустым") String searchString, Pageable pageable);
    Page<MiStandardDto> findAll(Pageable pageable);
    List<MiStandardDto> findAll();
    void update(@Valid MiStandardDto miStandardDto);
    void delete(long id);
}
