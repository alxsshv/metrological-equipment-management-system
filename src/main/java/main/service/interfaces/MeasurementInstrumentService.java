package main.service.interfaces;

import jakarta.validation.constraints.NotBlank;
import main.dto.rest.MiDto;
import main.dto.rest.MiFullDto;
import main.model.MeasurementInstrument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface MeasurementInstrumentService {
    MiFullDto findById(long id);
    MeasurementInstrument getMiById(long id);
    Page<MiDto> findBySearchString(@NotBlank(message = "Поле для поиска не может быть пустым") String searchString, Pageable pageable);
    List<MiDto> findBySearchString(@NotBlank(message = "Поле для поиска не может быть пустым") String searchString);
    Page<MiDto> findAll(Pageable pageable);
    List<MiDto> findAll();
    void deleteById(long id);
}
