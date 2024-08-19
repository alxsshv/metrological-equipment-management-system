package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import main.config.AppDefaults;
import main.dto.rest.MiStandardDto;
import main.dto.rest.mappers.MiDetailsMapper;
import main.dto.rest.mappers.MiStandardDtoMapper;
import main.exception.EntityAlreadyExistException;
import main.model.MiDetails;
import main.model.MiStandard;
import main.repository.MiStandardRepository;
import main.service.Category;
import main.service.interfaces.MiDetailsService;
import main.service.interfaces.MiStandardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@Getter
@Setter
@Service
@Validated
public class MiStandardServiceImpl implements MiStandardService {
    private final static Logger log = LoggerFactory.getLogger(MiStandardServiceImpl.class);
    private final MiStandardRepository miStandardRepository;
    private final MiDetailsService miDetailsService;
    private final FileServiceImpl fileService;


    @Override
    public void save(@Valid MiStandardDto miStandardDto, MultipartFile[] files, String[] descriptions) throws IOException {
            validateIfEntityAlreadyExist(miStandardDto.getArshinNumber());
            MiDetails parentMi = getParentMi(miStandardDto.getMiDetails().getId());
            MiStandard standard = MiStandardDtoMapper.mapToEntity(miStandardDto);
            standard.setMiDetails(parentMi);
            MiStandard savedMiStandard = miStandardRepository.save(standard);
            parentMi.setStatus(AppDefaults.getMiStatusStandard());
            miDetailsService.update(MiDetailsMapper.mapToDto(parentMi));
            uploadFilesIfFilesExist(files, descriptions, savedMiStandard.getId());
    }


    private void validateIfEntityAlreadyExist(String arshinNumber) throws EntityAlreadyExistException {
        MiStandard miStandardFromDb = miStandardRepository.findByArshinNumber(arshinNumber);
        if (miStandardFromDb != null){
            throw new EntityAlreadyExistException("Запись об эталоне с номером " + arshinNumber + " уже существует");
        }
    }


    private MiDetails getParentMi(long parentMiId){
        try {
            return miDetailsService.getById(parentMiId);
        } catch (EntityNotFoundException ex){
            throw new EntityNotFoundException("Выбранное в качестве эталона средство измерений не найдено");
        }
    }

    private void uploadFilesIfFilesExist(MultipartFile[] files, String[] descriptions, Long categoryId) throws IOException {
        if (files.length > 0) {
            fileService.uploadAllFiles(files, descriptions, Category.MI_STANDARD, categoryId);
        }
    }

    @Override
    public MiStandardDto findById(long id) {
            MiStandard miStandard = getMiStandardById(id);
            return MiStandardDtoMapper.mapToDto(miStandard);
    }

    public MiStandard getMiStandardById(long id){
        Optional<MiStandard> miStandardOpt = miStandardRepository.findById(id);
        if (miStandardOpt.isEmpty()){
            throw new EntityNotFoundException("Запись об эталоне № " + id + " не найдена");
        }
        return miStandardOpt.get();
    }


    @Override
    public Page<MiStandardDto> findBySearchString(@NotBlank(message = "Поле для поиска не может быть пустым") String searchString, Pageable pageable) {
            return miStandardRepository
                    .findByArshinNumberContainingOrSchemaTitleIgnoreCaseContainingOrSchemaNotationIgnoreCaseContaining(searchString.trim(), searchString.trim(), searchString.trim(), pageable)
                    .map(MiStandardDtoMapper::mapToDto);
    }

@Override
    public List<MiStandardDto> findBySearchString(@NotBlank(message = "Поле для поиска не может быть пустым") String searchString) {
            return miStandardRepository
                    .findByArshinNumberContainingOrSchemaTitleIgnoreCaseContainingOrSchemaNotationIgnoreCaseContaining(searchString.trim(), searchString.trim(), searchString.trim())
                    .stream().map(MiStandardDtoMapper::mapToDto).toList();

}


    @Override
    public Page<MiStandardDto> findAll(Pageable pageable) {
        return miStandardRepository.findAll(pageable).map(MiStandardDtoMapper ::mapToDto);
    }

    @Override
    public List<MiStandardDto> findAll() {
        return miStandardRepository.findAll().stream().map(MiStandardDtoMapper ::mapToDto).toList();
    }

    @Override
    public void update(@Valid MiStandardDto miStandardDto){
            MiStandard standardFromDB = getMiStandardById(miStandardDto.getId());
            MiStandard updateData = MiStandardDtoMapper.mapToEntity(miStandardDto);
            standardFromDB.updateFrom(updateData);
            miStandardRepository.save(standardFromDB);
    }

    @Override
    public void delete(long id){
        miStandardRepository.deleteById(id);
    }

}
