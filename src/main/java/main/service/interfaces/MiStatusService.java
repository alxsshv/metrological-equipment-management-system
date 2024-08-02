package main.service.interfaces;

import main.dto.rest.MiStatusDto;
import main.model.MiStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MiStatusService {
    void save(MiStatusDto miStatusDto);
    MiStatusDto findById(long id);
    MiStatus getById(long id);
    Page<MiStatusDto> findByStatus(String status, Pageable pageable);
    List<MiStatusDto> findByStatus(String status);
    Page<MiStatusDto> findAll(Pageable pageable);
    List<MiStatusDto> findAll();
    void update(MiStatusDto miStatusDto);
    void delete(long id);

}
