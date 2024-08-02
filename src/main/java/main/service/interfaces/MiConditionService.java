package main.service.interfaces;


import main.dto.rest.MiConditionDto;
import main.model.MiCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MiConditionService {
    void save(MiConditionDto miConditionDto);
    MiConditionDto findById(long id);
    MiCondition getById(long id);
    Page<MiConditionDto> findByTitle(String title, Pageable pageable);
    List<MiConditionDto> findByTitle(String title);
    Page<MiConditionDto> findAll(Pageable pageable);
    List<MiConditionDto> findAll();
    void update(MiConditionDto miConditionDto);
    void delete(long id);

}
