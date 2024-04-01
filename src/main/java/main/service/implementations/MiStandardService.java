package main.service.implementations;

import main.dto.MiStandardDto;
import main.dto.mappers.MiStandardDtoMapper;
import main.model.MiStandard;
import main.repository.MeasurementInstrumentRepository;
import main.repository.MiStandardRepository;
import main.service.ServiceMessage;
import main.service.interfaces.IMiStandardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class MiStandardService implements IMiStandardService {
    private final static Logger log = LoggerFactory.getLogger(MiStandardService.class);
    private final MiStandardRepository miStandardRepository;

    public MiStandardService(MiStandardRepository miStandardRepository,
                             MeasurementInstrumentRepository instrumentRepository) {
        this.miStandardRepository = miStandardRepository;
    }

    @Override
    public ResponseEntity<?> save(MiStandardDto miStandardDto){
        String errorMessage = checkMiStandardDtoComposition(miStandardDto);
        if (!errorMessage.isEmpty()) {
            log.info(errorMessage);
            return ResponseEntity.status(422).body(
                    new ServiceMessage(errorMessage));
        }
        MiStandard miStandardFromDb = miStandardRepository.findByArshinNumber(miStandardDto.getArshinNumber());
        if (miStandardFromDb != null){
            errorMessage = "Запись об эталоне с номером " + miStandardDto.getArshinNumber() + " уже существует";
            log.info(errorMessage);
            return ResponseEntity.status(422).body(
                    new ServiceMessage(errorMessage));
        }
        MiStandard standard = MiStandardDtoMapper.mapToEntity(miStandardDto);
        standard.setCreationDateTime(LocalDateTime.now());
        miStandardRepository.save(standard);
        String okMessage = "Запись об эталоне № " + miStandardDto.getArshinNumber() + " успешно добавлена";
        log.info(okMessage);
        return ResponseEntity.ok(
                new ServiceMessage(okMessage));
    }
    private String checkMiStandardDtoComposition(MiStandardDto dto){
        if (dto.getMeasurementInstrument() == null) {
            return "Некорректно указано средство измерений, применяемой в качестве эталона";
        }

        if (dto.getArshinNumber() == null || dto.getArshinNumber().isEmpty()){
            return "Пожалуйста укажите номер средства измерений в ФГИС\"Аршин\"";
        }

        return "";
    }

    @Override
    public ResponseEntity<?> update(MiStandardDto miStandardDto){
        String errorMessage = checkMiStandardDtoComposition(miStandardDto);
        if (!errorMessage.isEmpty()) {
            log.info(errorMessage);
            return ResponseEntity.status(422).body(new ServiceMessage(errorMessage));
        }
        Optional<MiStandard> miStandardOpt = miStandardRepository.findById(miStandardDto.getId());
        if (miStandardOpt.isEmpty()){
            errorMessage = "Запись об эталоне № " + miStandardDto.getArshinNumber() + " не найдена";
            log.info(errorMessage);
            return ResponseEntity.status(404).body(new ServiceMessage(errorMessage));
        }
        MiStandard updateData = MiStandardDtoMapper.mapToEntity(miStandardDto);
        MiStandard standard = miStandardOpt.get();
        standard.updateFrom(updateData);
        miStandardRepository.save(standard);
        String okMessage ="Cведения об эталоне № " + miStandardDto.getArshinNumber() +  " обновлены";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }

    @Override
    public ResponseEntity<?>delete(long id){
        Optional<MiStandard> miStandardOpt = miStandardRepository.findById(id);
        if (miStandardOpt.isEmpty()){
            String errorMessage = "Данные для удаления не найдены";
            log.info(errorMessage);
            return ResponseEntity.status(404).body(new ServiceMessage(errorMessage));
        }
        miStandardRepository.delete(miStandardOpt.get());
        String okMessage ="Запись об эталоне № " + miStandardOpt.get().getArshinNumber() + " успешно удалена";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }

    @Override
    public ResponseEntity<?> findById(long id) {
        Optional<MiStandard> miStandardOpt = miStandardRepository.findById(id);
        if (miStandardOpt.isPresent()) {
            MiStandardDto miStandardDto = MiStandardDtoMapper.mapToDto(miStandardOpt.get());
            return ResponseEntity.ok(miStandardDto);
        } else {
            return ResponseEntity.notFound().build();
        }
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
        if (searchString == null || searchString.isEmpty()){
            String errorMessage = "Поле для поиска не может быть пустым";
            log.info(errorMessage);
            return ResponseEntity.status(400).body(new ServiceMessage(errorMessage));
        }
        Page<MiStandardDto> page =  miStandardRepository
                .findByArshinNumberContainingOrSchemaTitleContaining(searchString.trim(),searchString.trim(), pageable)
                .map(MiStandardDtoMapper::mapToDto);
        return ResponseEntity.ok(page);
    }

    @Override
    public Page<MiStandardDto> findAll(Pageable pageable) {
        return miStandardRepository.findAll(pageable).map(MiStandardDtoMapper ::mapToDto);
    }

    @Override
    public List<MiStandardDto> findAll() {
        return miStandardRepository.findAll().stream().map(MiStandardDtoMapper ::mapToDto).toList();
    }

}
