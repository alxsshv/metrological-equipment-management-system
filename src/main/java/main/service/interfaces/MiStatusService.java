package main.service.interfaces;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import main.dto.rest.MiStatusDto;
import main.model.MiStatus;
import main.service.validators.MiStatusAlreadyExist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MiStatusService {
    void save(@MiStatusAlreadyExist @Valid MiStatusDto miStatusDto);
    MiStatusDto findById(long id);
    MiStatus getById(long id);
    Page<MiStatusDto> findByStatus(@NotBlank(message = "Поле для поиска не может быть пустым") String status, Pageable pageable);
    List<MiStatusDto> findByStatus(@NotBlank(message = "Поле для поиска не может быть пустым") String status);
    Page<MiStatusDto> findAll(Pageable pageable);
    List<MiStatusDto> findAll();
    void update(@Valid MiStatusDto miStatusDto);
    void delete(long id);

}
