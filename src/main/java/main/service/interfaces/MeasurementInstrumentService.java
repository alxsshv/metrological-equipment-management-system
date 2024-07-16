package main.service.interfaces;

import main.dto.rest.MiDto;
import main.dto.rest.MiFullDto;
import main.exception.DtoCompositionException;
import main.exception.EntityAlreadyExistException;
import main.model.MeasurementInstrument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MeasurementInstrumentService {
    void save(MiFullDto instrumentDto, MultipartFile[] files, String[] descriptions) throws IOException, EntityAlreadyExistException, DtoCompositionException;
    MiFullDto findById(long id);
    MeasurementInstrument getMiById(long id);
    Page<MiDto> findBySearchString(String searchString, Pageable pageable);
    List<MiDto> findBySearchString(String searchString);
    Page<MiDto> findAll(Pageable pageable);
    List<MiDto> findAll();
    void update(MiFullDto instrumentDto);
    void delete(long id);
}
