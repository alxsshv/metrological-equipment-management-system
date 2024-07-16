package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import main.dto.rest.MiDto;
import main.dto.rest.MiFullDto;
import main.dto.rest.mappers.MeasurementInstrumentMapper;
import main.exception.DtoCompositionException;
import main.exception.EntityAlreadyExistException;
import main.exception.ParameterNotValidException;
import main.model.MeasurementInstrument;
import main.model.MiType;
import main.model.Organization;
import main.repository.MeasurementInstrumentRepository;
import main.repository.MiTypeRepository;
import main.repository.OrganizationRepository;
import main.service.Category;
import main.service.interfaces.MeasurementInstrumentService;
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
@Service
public class MiServiceImpl implements MeasurementInstrumentService {
    public static final Logger log = LoggerFactory.getLogger(MiServiceImpl.class);
    private final MeasurementInstrumentRepository measurementInstrumentRepository;
    private final OrganizationRepository organizationRepository;
    private final MiTypeRepository miTypeRepository;
    private final FileServiceImpl fileService;


    @Override
    public void save(MiFullDto instrumentDto, MultipartFile[] files, String[] descriptions) throws IOException, EntityAlreadyExistException, DtoCompositionException {
            checkMeasurementInstrumentDtoComposition(instrumentDto);
            validateDtoMiOwnerAndReturnDefaultIfNull(instrumentDto);
            validateIfEntityAlreadyExist(instrumentDto);
            MeasurementInstrument instrument = MeasurementInstrumentMapper.mapToEntity(instrumentDto);
            MeasurementInstrument savedInstrument = measurementInstrumentRepository.save(instrument);
            uploadFilesIfFilesExist(files, descriptions, savedInstrument.getId());
    }

    private void checkMeasurementInstrumentDtoComposition(MiFullDto dto) throws DtoCompositionException {
        if (dto.getModification() == null || dto.getModification().isEmpty()) {
            throw new DtoCompositionException("Некорректно указана мдификация средства измерений");
        }
        if (dto.getSerialNum() == null || dto.getSerialNum().isEmpty()){
            throw new DtoCompositionException("Пожалуйста заполните заводской номер средства измерений");
        }
        if (dto.getMiType() == null){
            throw new DtoCompositionException("Пожалуйста укажите тип средства измерений");
        }

        Optional<MiType> miTypeFromDb = miTypeRepository.findById(dto.getMiType().getId());
        if (miTypeFromDb.isEmpty()){
            throw new DtoCompositionException("Выбранный тип средства измерений отсутствует в базе данных пожалуйста выберите имеющийся тип" +
                    " средства измерений  или добавьте требуемый тип средства измерений в базу");
        }
    }

     private void validateDtoMiOwnerAndReturnDefaultIfNull(MiFullDto dto) throws DtoCompositionException {
         if (dto.getOwner() == null){
             Optional<Organization> defaultOwnerOpt = organizationRepository.findById(1L);
             defaultOwnerOpt.ifPresent(dto::setOwner);
             return;
         }
         Optional<Organization> ownerFromDb = organizationRepository.findById(dto.getOwner().getId());
         if (ownerFromDb.isEmpty()){
             throw new DtoCompositionException("Указанная организация отсутствует в базе данных, пожалуйста проверьте правильность выбора " +
                     "организации или добавьте требуемую организацию в базу данных");
         }
     }

    private void validateIfEntityAlreadyExist(MiFullDto miFullDto) throws EntityAlreadyExistException {
        MeasurementInstrument instrumentFromDb = measurementInstrumentRepository
                .findByModificationAndSerialNum(miFullDto.getModification(), miFullDto.getSerialNum());
        if (instrumentFromDb != null){
            throw new EntityAlreadyExistException("Данная модификация средства измерений с заводским номером "
                    + miFullDto.getSerialNum() + " уже существует");
        }
    }

    private void uploadFilesIfFilesExist(MultipartFile[] files, String[] descriptions, Long categoryId) throws IOException {
        if (files.length > 0) {
            fileService.uploadAllFiles(files, descriptions, Category.MEASUREMENT_INSTRUMENT, categoryId);
        }
    }

    @Override
    public MiFullDto findById(long id) {
        MeasurementInstrument instrument = getMiById(id);
       return MeasurementInstrumentMapper.mapToFullDto(instrument);
    }

    public MeasurementInstrument getMiById(long id){
        Optional<MeasurementInstrument> instrumentOpt = measurementInstrumentRepository.findById(id);
        if (instrumentOpt.isEmpty()){
            throw new EntityNotFoundException("Средство измерений № "+ id +" не найдено");
        }
        return instrumentOpt.get();
    }

    @Override
    public Page<MiDto> findBySearchString(String searchString, Pageable pageable) {
            return measurementInstrumentRepository
                    .findByModificationIgnoreCaseContainingOrSerialNumIgnoreCaseContainingOrInventoryNumIgnoreCaseContaining(
                            searchString.trim(), searchString.trim(), searchString.trim(), pageable)
                    .map(MeasurementInstrumentMapper::mapToDto);
    }

    @Override
    public List<MiDto> findBySearchString(String searchString) {
            validateSearchString(searchString);
            return measurementInstrumentRepository
                    .findByModificationIgnoreCaseContainingOrSerialNumIgnoreCaseContainingOrInventoryNumIgnoreCaseContaining(
                            searchString.trim(), searchString.trim(), searchString.trim())
                    .stream().map(MeasurementInstrumentMapper::mapToDto).toList();
    }

    private void validateSearchString(String searchString) throws ParameterNotValidException {
        if (searchString == null || searchString.isEmpty()) {
            throw new ParameterNotValidException("Поле для поиска не может быть пустым");
        }
    }

    @Override
    public Page<MiDto> findAll(Pageable pageable) {
        return measurementInstrumentRepository.findAll(pageable).map(MeasurementInstrumentMapper ::mapToDto);
    }

    @Override
    public List<MiDto> findAll() {
        return measurementInstrumentRepository.findAll().stream().map(MeasurementInstrumentMapper::mapToDto).toList();
    }

    @Override
    public void update(MiFullDto instrumentDto) {
            checkMeasurementInstrumentDtoComposition(instrumentDto);
            MeasurementInstrument instrumentFromDb = getMiById(instrumentDto.getId());
            MeasurementInstrument updatingMeasurementInstrumentData = MeasurementInstrumentMapper
                    .mapToEntity(instrumentDto);
            instrumentFromDb.updateFrom(updatingMeasurementInstrumentData);
            measurementInstrumentRepository.save(instrumentFromDb);
    }

    @Override
    public void delete(long id) {
            try {
                getMiById(id);
                fileService.deleteAllFiles(Category.MEASUREMENT_INSTRUMENT, id);
                measurementInstrumentRepository.deleteById(id);
            } catch (IOException ex) {
                String errorMessage = "Ошибка доступа к файлу";
                log.error("{}:{}", errorMessage, ex.getMessage());
                throw new RuntimeException(errorMessage + ":" + ex.getMessage());
            }
        }
}
