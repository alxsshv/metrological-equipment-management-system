package main.service.interfaces;

import main.dto.rest.MiStandardDto;
import main.model.MiStandard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MiStandardService {
    void save(MiStandardDto miStandardDto, MultipartFile[] files, String[] descriptions) throws IOException;
    MiStandard getMiStandardById(long id);
    MiStandardDto findById(long id);
    List<MiStandardDto> findBySearchString(String searchString);
    Page<MiStandardDto> findBySearchString(String searchString, Pageable pageable);
    Page<MiStandardDto> findAll(Pageable pageable);
    List<MiStandardDto> findAll();
    void update(MiStandardDto miStandardDto);
    void delete(long id);
}
