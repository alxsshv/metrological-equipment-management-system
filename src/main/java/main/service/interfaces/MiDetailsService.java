package main.service.interfaces;


import jakarta.validation.Valid;
import main.dto.rest.MiDetailsDto;
import main.model.MiDetails;
import main.service.validators.MiAlreadyExist;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MiDetailsService {
    void save(@MiAlreadyExist @Valid MiDetailsDto miDetailsDto, MultipartFile[] files, String[] descriptions) throws IOException;
    MiDetailsDto findById(long id);
    MiDetails getById(long id);
    void update(MiDetailsDto miDetailsDto);
    void delete(long id);
}
