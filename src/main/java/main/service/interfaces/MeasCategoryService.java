package main.service.interfaces;

import main.dto.rest.MeasCategoryDto;
import main.model.MeasCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MeasCategoryService {
    void save(MeasCategoryDto measCategoryDto);
    MeasCategoryDto findById(long id);
    MeasCategory getById(long id);
    Page<MeasCategoryDto> findByTitle(String title, Pageable pageable);
    List<MeasCategoryDto> findByTitle(String title);
    Page<MeasCategoryDto> findAll(Pageable pageable);
    List<MeasCategoryDto> findAll();
    void update(MeasCategoryDto measCategoryDto);
    void delete(long id);

}
