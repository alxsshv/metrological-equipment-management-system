package main.service.interfaces;

import jakarta.validation.constraints.NotBlank;
import main.dto.rest.MiTypeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface MiTypeService {
    MiTypeDto findByNumber(@NotBlank(message = "Номер в ФИФ ОЕИ не может быть пустым") String number);
    Page<MiTypeDto> findBySearchString(@NotBlank(message = "Поле для поиска не может быть пустым") String searchString, Pageable pageable);
    List<MiTypeDto> findBySearchString(@NotBlank(message = "Поле для поиска не может быть пустым") String searchString);
    Page<MiTypeDto> findAll(Pageable pageable);
    List<MiTypeDto> findAll();
    void deleteById(long id) throws IOException;
}
