package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import main.dto.rest.MiStandardDto;
import main.dto.rest.mappers.MiStandardDtoMapper;
import main.exception.DtoCompositionException;
import main.exception.EntityAlreadyExistException;
import main.exception.ParameterNotValidException;
import main.model.MeasurementInstrument;
import main.model.MiStandard;
import main.repository.MiStandardRepository;
import main.service.Category;
import main.service.ServiceMessage;
import main.service.interfaces.MeasurementInstrumentService;
import main.service.interfaces.MiStandardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
    private final MeasurementInstrumentService measurementInstrumentService;
    private final FileServiceImpl fileService;


    @Override
    public ResponseEntity<?> save(MiStandardDto miStandardDto, MultipartFile[] files, String[] descriptions) throws IOException {
        try {
            checkMiStandardDtoComposition(miStandardDto);
            checkIfEntityAlreadyExist(miStandardDto.getArshinNumber());
            MeasurementInstrument parentMi = getParentMi(miStandardDto.getMeasurementInstrument().getId());
            MiStandard standard = MiStandardDtoMapper.mapToEntity(miStandardDto);
            standard.setMeasurementInstrument(parentMi);
            MiStandard savedMiStandard = miStandardRepository.save(standard);
            uploadFilesIfFilesExist(files, descriptions, savedMiStandard.getId());
            String okMessage = "Запись об эталоне № " + miStandardDto.getArshinNumber() + " успешно добавлена";
            log.info(okMessage);
            return ResponseEntity.status(201).body(new ServiceMessage(okMessage));
        } catch(EntityAlreadyExistException | EntityNotFoundException | DtoCompositionException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(400).body(
                    new ServiceMessage(ex.getMessage()));
        }
    }

    private void checkMiStandardDtoComposition(MiStandardDto dto) throws DtoCompositionException {
        if (dto.getMeasurementInstrument() == null) {
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

    private MeasurementInstrument getParentMi(long parentMiId){
        try {
            return measurementInstrumentService.getMiById(parentMiId);
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
    public ResponseEntity<?> findById(long id) {
        try {
            MiStandard miStandard = getMiStandardById(id);
            MiStandardDto miStandardDto = MiStandardDtoMapper.mapToDto(miStandard);
            return ResponseEntity.ok(miStandardDto);
        } catch (EntityNotFoundException ex){
            log.info(ex.getMessage());
            return ResponseEntity.status(404).body(ex.getMessage());
        }
    }

    public MiStandard getMiStandardById(long id){
        Optional<MiStandard> miStandardOpt = miStandardRepository.findById(id);
        if (miStandardOpt.isEmpty()){
            throw new EntityNotFoundException("Запись об эталоне № " + id + " не найдена");
        }
        return miStandardOpt.get();
    }

    @Override
    public ResponseEntity<?> findByArshinNumber(String arshinNumber) {
        MiStandard standard = miStandardRepository.findByArshinNumber(arshinNumber);
        if (standard != null) {
            MiStandardDto miStandardDto = MiStandardDtoMapper.mapToDto(standard);
            return ResponseEntity.ok(miStandardDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<?> findBySearchString(String searchString, Pageable pageable) {
        try {
            validateSearchString(searchString);
            Page<MiStandardDto> page = miStandardRepository
                    .findByArshinNumberContainingOrSchemaTitleIgnoreCaseContainingOrSchemaNotationIgnoreCaseContaining(searchString.trim(), searchString.trim(), searchString.trim(), pageable)
                    .map(MiStandardDtoMapper::mapToDto);
            return ResponseEntity.ok(page);
        } catch (ParameterNotValidException ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(400).body(new ServiceMessage(ex.getMessage()));
        }
    }

@Override
    public ResponseEntity<?> findBySearchString(String searchString) {
        try {
            validateSearchString(searchString);
            List<MiStandardDto> dtos = miStandardRepository
                    .findByArshinNumberContainingOrSchemaTitleIgnoreCaseContainingOrSchemaNotationIgnoreCaseContaining(searchString.trim(), searchString.trim(), searchString.trim())
                    .stream().map(MiStandardDtoMapper::mapToDto).toList();
            return ResponseEntity.ok(dtos);
        } catch (ParameterNotValidException ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(400).body(new ServiceMessage(ex.getMessage()));
        }

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
    public ResponseEntity<?> update(MiStandardDto miStandardDto){
        try {
            checkMiStandardDtoComposition(miStandardDto);
            MiStandard standardFromDB = getMiStandardById(miStandardDto.getId());
            MiStandard updateData = MiStandardDtoMapper.mapToEntity(miStandardDto);
            standardFromDB.updateFrom(updateData);
            miStandardRepository.save(standardFromDB);
            String okMessage = "Cведения об эталоне № " + miStandardDto.getArshinNumber() + " обновлены";
            log.info(okMessage);
            return ResponseEntity.ok(new ServiceMessage(okMessage));
        } catch (EntityNotFoundException | DtoCompositionException ex){
            log.info(ex.getMessage());
            return ResponseEntity.status(400).body(new ServiceMessage(ex.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?>delete(long id){
        miStandardRepository.deleteById(id);
        String okMessage ="Запись об эталоне № " + id + " успешно удалена";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }

}
