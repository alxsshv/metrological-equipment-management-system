package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import main.config.AppDefaults;
import main.dto.rest.MiDetailsDto;
import main.dto.rest.MiStandardDto;
import main.dto.rest.mappers.MiDetailsMapper;
import main.dto.rest.mappers.MiStandardDtoMapper;
import main.exception.DtoCompositionException;
import main.exception.EntityAlreadyExistException;
import main.exception.ParameterNotValidException;
import main.model.MeasurementInstrument;
import main.model.MiDetails;
import main.model.MiStandard;
import main.repository.MiStandardRepository;
import main.service.Category;
import main.service.interfaces.MeasurementInstrumentService;
import main.service.interfaces.MiDetailsService;
import main.service.interfaces.MiStandardService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
@AllArgsConstructor
@Getter
@Setter
@Service
public class MiStandardServiceImpl implements MiStandardService {
    private final static Logger log = LoggerFactory.getLogger(MiStandardServiceImpl.class);
    private final MiStandardRepository miStandardRepository;
    private final MiDetailsService miDetailsService;
    private final FileServiceImpl fileService;


    @Override
    public void save(MiStandardDto miStandardDto, MultipartFile[] files, String[] descriptions) throws IOException {
            checkMiStandardDtoComposition(miStandardDto);
            checkIfEntityAlreadyExist(miStandardDto.getArshinNumber());
            MiDetails parentMi = getParentMi(miStandardDto.getMiDetails().getId());
            MiStandard standard = MiStandardDtoMapper.mapToEntity(miStandardDto);
            standard.setMiDetails(parentMi);
            MiStandard savedMiStandard = miStandardRepository.save(standard);
            parentMi.setStatus(AppDefaults.getMiStatusStandard());
            miDetailsService.update(MiDetailsMapper.mapToDto(parentMi));
            uploadFilesIfFilesExist(files, descriptions, savedMiStandard.getId());
    }


    private void checkMiStandardDtoComposition(MiStandardDto dto) throws DtoCompositionException {
        if (dto.getMiDetails() == null) {
            throw new DtoCompositionException("Некорректно указано средство измерений, применяемое в качестве эталона");
        }
        if (dto.getArshinNumber() == null || dto.getArshinNumber().isEmpty()){
            throw new DtoCompositionException("Пожалуйста укажите регистрационный номер эталона в ФГИС\"Аршин\"");
        }
    }

    private void checkIfEntityAlreadyExist(String arshinNumber) throws EntityAlreadyExistException {
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
    public Page<MiStandardDto> findBySearchString(String searchString, Pageable pageable) {
            validateSearchString(searchString);
            return miStandardRepository
                    .findByArshinNumberContainingOrSchemaTitleIgnoreCaseContainingOrSchemaNotationIgnoreCaseContaining(searchString.trim(), searchString.trim(), searchString.trim(), pageable)
                    .map(MiStandardDtoMapper::mapToDto);
    }

@Override
    public List<MiStandardDto> findBySearchString(String searchString) {
            validateSearchString(searchString);
            return miStandardRepository
                    .findByArshinNumberContainingOrSchemaTitleIgnoreCaseContainingOrSchemaNotationIgnoreCaseContaining(searchString.trim(), searchString.trim(), searchString.trim())
                    .stream().map(MiStandardDtoMapper::mapToDto).toList();

}

    private void validateSearchString(String searchString) throws ParameterNotValidException {
        if (searchString == null || searchString.isEmpty()) {
            throw new ParameterNotValidException("Поле для поиска не может быть пустым");
        }
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
    public void update(MiStandardDto miStandardDto){
            checkMiStandardDtoComposition(miStandardDto);
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
