package main.service.interfaces;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import main.dto.rest.MeasCategoryDto;
import main.model.MeasCategory;
import main.service.validators.MeasCategoryAlreadyExist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MeasCategoryService {
    void save(@MeasCategoryAlreadyExist @Valid MeasCategoryDto measCategoryDto);
    MeasCategoryDto findById(long id);
    MeasCategory getById(long id);
    Page<MeasCategoryDto> findByTitle(@NotBlank(message = "Поле для поиска не может быть пустым") String title, Pageable pageable);
    List<MeasCategoryDto> findByTitle(@NotBlank(message = "Поле для поиска не может быть пустым") String title);
    Page<MeasCategoryDto> findAll(Pageable pageable);
    List<MeasCategoryDto> findAll();
    void update(@Valid MeasCategoryDto measCategoryDto);
    void delete(long id);

}
