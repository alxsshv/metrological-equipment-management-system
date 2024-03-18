package main.service.measurement_instrument;

import main.dto.MeasurementInstrumentDto;
import main.dto.mappers.MeasurementInstrumentMapper;
import main.model.MeasurementInstrument;
import main.repository.MeasurementInstrumentRepository;
import main.service.ServiceMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MeasurementInstrumentService implements IMeasurementInstrumentService {
    public static final Logger log = LoggerFactory.getLogger(MeasurementInstrumentService.class);
    private final MeasurementInstrumentRepository measurementInstrumentRepository;

    public MeasurementInstrumentService(MeasurementInstrumentRepository measurementInstrumentRepository) {
        this.measurementInstrumentRepository = measurementInstrumentRepository;
    }

    @Override
    public ResponseEntity<?> save(MeasurementInstrumentDto instrumentDto) {
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
            return ResponseEntity.status(422).body(
                    new ServiceMessage(errorMessage));
        }
        measurementInstrumentRepository.save(MeasurementInstrumentMapper.mapToEntity(instrumentDto));
        String okMessage = "Запись о средстве измерений " + instrumentDto.getModification() + " зав. № " +
                instrumentDto.getSerialNum() + " успешно добавлена";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));

    }

    private String checkMeasurementInstrumentDtoComposition(MeasurementInstrumentDto dto){
        if (dto.getModification() == null || dto.getModification().isEmpty()) {
            return "Некорректно указана мдификация средства измерений";
        }
        if (dto.getSerialNum() == null || dto.getSerialNum().isEmpty()){
            return "Пожалуйста заполните заводской номер средства измерений";
        }
        return "";
    }

    @Override
    public ResponseEntity<?> update(MeasurementInstrumentDto instrumentDto) {
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
    public ResponseEntity<?> delete(int id) {
        Optional<MeasurementInstrument> instrumentOpt = measurementInstrumentRepository.findById(id);
        if (instrumentOpt.isEmpty()){
            String errorMessage = "Данные для удаления не найдены";
            log.info(errorMessage);
            return ResponseEntity.status(404).body(new ServiceMessage(errorMessage));
        }
        measurementInstrumentRepository.delete(instrumentOpt.get());
        String okMessage ="Запись о средстве измерений" + instrumentOpt.get().getModification() +" зав. № "
                + instrumentOpt.get().getSerialNum() + " успешно удалена";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }

    @Override
    public ResponseEntity<?> findById(int id) {
        Optional<MeasurementInstrument> instrumentOpt = measurementInstrumentRepository.findById(id);
        if (instrumentOpt.isPresent()) {
            MeasurementInstrumentDto dto = MeasurementInstrumentMapper.mapToDto(instrumentOpt.get());
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
        Page<MeasurementInstrumentDto> page =  measurementInstrumentRepository
                .findByModificationOrSerialNumOrInventoryNumContaining(
                        searchString.trim(),searchString.trim(),searchString.trim(), pageable)
                .map(MeasurementInstrumentMapper::mapToDto);
        return ResponseEntity.ok(page);
    }

    @Override
    public Page<MeasurementInstrumentDto> findAll(Pageable pageable) {
        return measurementInstrumentRepository.findAll(pageable).map(MeasurementInstrumentMapper ::mapToDto);
    }

    @Override
    public List<MeasurementInstrumentDto> findAll() {
        return measurementInstrumentRepository.findAll().stream().map(MeasurementInstrumentMapper::mapToDto).toList();
    }
}
