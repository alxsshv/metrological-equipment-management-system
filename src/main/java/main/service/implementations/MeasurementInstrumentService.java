package main.service.implementations;

import main.dto.MiDto;
import main.dto.MiFullDto;
import main.dto.mappers.MeasurementInstrumentMapper;
import main.model.MeasurementInstrument;
import main.model.MiType;
import main.model.Organization;
import main.repository.MeasurementInstrumentRepository;
import main.repository.MiTypeRepository;
import main.repository.OrganizationRepository;
import main.service.Category;
import main.service.ServiceMessage;
import main.service.interfaces.IMeasurementInstrumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MeasurementInstrumentService implements IMeasurementInstrumentService {
    public static final Logger log = LoggerFactory.getLogger(MeasurementInstrumentService.class);
    private final MeasurementInstrumentRepository measurementInstrumentRepository;
    private final OrganizationRepository organizationRepository;
    private final MiTypeRepository miTypeRepository;
    private final FileService fileService;

    public MeasurementInstrumentService(MeasurementInstrumentRepository measurementInstrumentRepository,
                                        OrganizationRepository organizationRepository,
                                        MiTypeRepository miTypeRepository,
                                        FileService fileService) {
        this.measurementInstrumentRepository = measurementInstrumentRepository;
        this.organizationRepository = organizationRepository;
        this.miTypeRepository = miTypeRepository;
        this.fileService = fileService;
    }

    @Override
    public ResponseEntity<?> save(MiFullDto instrumentDto, MultipartFile[] files, String[] descriptions) throws IOException {
        String errorMessage = checkMeasurementInstrumentDtoComposition(instrumentDto);
        if (!errorMessage.isEmpty()) {
            log.info(errorMessage);
            return ResponseEntity.status(422).body(
                    new ServiceMessage(errorMessage));
        }
        MeasurementInstrument instrumentFromDb = measurementInstrumentRepository
                .findByModificationAndSerialNum(instrumentDto.getModification(), instrumentDto.getSerialNum());
        if (instrumentFromDb != null){
            errorMessage = "Данная модификация средства измерений с заводским номером " + instrumentDto.getSerialNum() + " уже существует";
            log.info(errorMessage);
            return ResponseEntity.status(422).body(new ServiceMessage(errorMessage));
        }
        MeasurementInstrument instrument = MeasurementInstrumentMapper.mapToEntity(instrumentDto);
        instrument.setCreationDateTime(LocalDateTime.now());
        MeasurementInstrument savedInstrument =  measurementInstrumentRepository.save(instrument);
        fileService.uploadAllFiles(files,descriptions, Category.MEASUREMENT_INSTRUMENT,savedInstrument.getId());
        String okMessage = "Запись о средстве измерений " + instrumentDto.getModification() + " зав. № " +
                instrumentDto.getSerialNum() + " успешно добавлена";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));

    }

    private String checkMeasurementInstrumentDtoComposition(MiFullDto dto){
        if (dto.getModification() == null || dto.getModification().isEmpty()) {
            return "Некорректно указана мдификация средства измерений";
        }
        if (dto.getSerialNum() == null || dto.getSerialNum().isEmpty()){
            return "Пожалуйста заполните заводской номер средства измерений";
        }

        if (dto.getMiType() == null){
            return "Пожалуйста укажите тип средства измерений";
        }
        if (dto.getOwner() == null){
            return "Пожалуйста укажите владельца средства измерений";
        }
        Optional<MiType> miTypeFromDb = miTypeRepository.findById(dto.getMiType().getId());
        if (miTypeFromDb.isEmpty()){
            return "Выбранный тип средства измерений отсутствует в базе данных пожалуйста выберите имеющийся тип" +
                    " средства измерений  или добавьте требуемый тип средства измерений в базу";
        }
        Optional<Organization> ownerFromDb = organizationRepository.findById(dto.getOwner().getId());
        if (ownerFromDb.isEmpty()){
            return "Указанная организация отсутствует в базе данных, пожалуйста проверьте правильность выбора " +
                    "организации или добавьте требуемую организацию в базу данных";
        }

        return "";
    }

    @Override
    public ResponseEntity<?> update(MiFullDto instrumentDto) {
        String errorMessage = checkMeasurementInstrumentDtoComposition(instrumentDto);
        if (!errorMessage.isEmpty()) {
            log.info(errorMessage);
            return ResponseEntity.status(422).body(new ServiceMessage(errorMessage));
        }

        Optional<MeasurementInstrument> userOpt = measurementInstrumentRepository.findById(instrumentDto.getId());
        if (userOpt.isEmpty()){
            errorMessage = "Запись о средстве измерений не найдена";
            log.info(errorMessage);
            return ResponseEntity.status(404).body(new ServiceMessage(errorMessage));
        }
        MeasurementInstrument updatingMeasurementInstrumentData = MeasurementInstrumentMapper
                .mapToEntity(instrumentDto);
        MeasurementInstrument instrument = userOpt.get();
        instrument.updateFrom(updatingMeasurementInstrumentData);
        measurementInstrumentRepository.save(instrument);
        String okMessage ="Cведения о средстве измерений успешно обновлены";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));

    }

    @Override
    public ResponseEntity<?> delete(long id) {
        Optional<MeasurementInstrument> instrumentOpt = measurementInstrumentRepository.findById(id);
        if (instrumentOpt.isEmpty()){
            String errorMessage = "Данные для удаления не найдены";
            log.info(errorMessage);
            return ResponseEntity.status(404).body(new ServiceMessage(errorMessage));
        }
        measurementInstrumentRepository.delete(instrumentOpt.get());
        String okMessage ="Запись о средстве измерений " + instrumentOpt.get().getModification() +" зав. № "
                + instrumentOpt.get().getSerialNum() + " успешно удалена";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }

    @Override
    public ResponseEntity<?> findById(long id) {
        Optional<MeasurementInstrument> instrumentOpt = measurementInstrumentRepository.findById(id);
        if (instrumentOpt.isPresent()) {
            MiFullDto dto = MeasurementInstrumentMapper.mapToFullDto(instrumentOpt.get());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<?> findBySearchString(String searchString, Pageable pageable) {
        if (searchString == null || searchString.isEmpty()){
            String errorMessage = "Поле для поиска не может быть пустым";
            log.info(errorMessage);
            return ResponseEntity.status(400).body(new ServiceMessage(errorMessage));
        }
        Page<MiDto> page =  measurementInstrumentRepository
                .findByModificationContainingOrSerialNumContainingOrInventoryNumContaining(
                        searchString.trim(),searchString.trim(),searchString.trim(), pageable)
                .map(MeasurementInstrumentMapper::mapToDto);
        return ResponseEntity.ok(page);
    }

    @Override
    public ResponseEntity<?> findBySearchString(String searchString) {
        if (searchString == null || searchString.isEmpty()){
            String errorMessage = "Поле для поиска не может быть пустым";
            log.info(errorMessage);
            return ResponseEntity.status(400).body(new ServiceMessage(errorMessage));
        }
        List<MiDto> instruments =  measurementInstrumentRepository
                .findByModificationContainingOrSerialNumContainingOrInventoryNumContaining(
                        searchString.trim(),searchString.trim(),searchString.trim())
                .stream().map(MeasurementInstrumentMapper::mapToDto).toList();
        return ResponseEntity.ok(instruments);
    }

    @Override
    public Page<MiDto> findAll(Pageable pageable) {
        return measurementInstrumentRepository.findAll(pageable).map(MeasurementInstrumentMapper ::mapToDto);
    }

    @Override
    public List<MiDto> findAll() {
        return measurementInstrumentRepository.findAll().stream().map(MeasurementInstrumentMapper::mapToDto).toList();
    }
}
