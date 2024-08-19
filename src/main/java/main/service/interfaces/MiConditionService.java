package main.service.interfaces;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import main.dto.rest.MiConditionDto;
import main.model.MiCondition;
import main.service.validators.MiConditionAlreadyExist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MiConditionService {
    void save(@MiConditionAlreadyExist @Valid MiConditionDto miConditionDto);
    MiConditionDto findById(long id);
    MiCondition getById(long id);
    Page<MiConditionDto> findByTitle(@NotBlank(message = "Поле для поиска не может быть пустым") String title, Pageable pageable);
    List<MiConditionDto> findByTitle(@NotBlank(message = "Поле для поиска не может быть пустым") String title);
    Page<MiConditionDto> findAll(Pageable pageable);
    List<MiConditionDto> findAll();
    void update(@Valid MiConditionDto miConditionDto);
    void delete(long id);

}
