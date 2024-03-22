package main.service.mi_type;

import main.dto.MiTypeDto;
import main.dto.MiTypeFullDto;
import main.dto.mappers.MiTypeDtoMapper;
import main.model.MiType;
import main.model.MiTypeInstruction;
import main.model.MiTypeModification;
import main.repository.MiTypeInstructionRepository;
import main.repository.MiTypeModificationRepository;
import main.repository.MiTypeRepository;
import main.service.ServiceMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MiTypeService implements IMiTypeService {
    public static final Logger log = LoggerFactory.getLogger(MiTypeService.class);
    private final MiTypeRepository miTypeRepository;
    private final MiTypeInstructionRepository miTypeInstructionRepository;
    private final MiTypeModificationRepository miTypeModificationRepository;
    @Value("${upload.images.path}")
    private String imageUploadPath;

    public MiTypeService(MiTypeRepository miTypeRepository, MiTypeInstructionRepository miTypeInstructionRepository,
                         MiTypeModificationRepository miTypeModificationRepository) {
        this.miTypeRepository = miTypeRepository;
        this.miTypeInstructionRepository = miTypeInstructionRepository;
        this.miTypeModificationRepository = miTypeModificationRepository;
    }

    @Override
    public ResponseEntity<?> save(MiTypeFullDto miTypeDto){
        String errorMessage = checkMiTypeDtoComposition(miTypeDto);
        if (!errorMessage.isEmpty()) {
            log.info(errorMessage);
            return ResponseEntity.status(422).body(
                    new ServiceMessage(errorMessage));
        }
        MiType miTypeFromDb = miTypeRepository.findByNumber(miTypeDto.getNumber());
        if (miTypeFromDb != null){
            errorMessage = "Запись о типе СИ № " + miTypeDto.getNumber() + " уже существует";
            log.info(errorMessage);
            return ResponseEntity.status(422).body(
                    new ServiceMessage(errorMessage));
        }
        miTypeInstructionRepository.save(MiTypeDtoMapper.mapToEntity(miTypeDto));
        String okMessage = "Запись о типе СИ № " + miTypeDto.getNumber() + " успешно добавлена";
        log.info(okMessage);
        return ResponseEntity.ok(
                new ServiceMessage(okMessage));
    }
    private String checkMiTypeDtoComposition(MiTypeFullDto dto){
        String miTypeNumberTemplate = "[0-9]{5}-[0-9]{2}";
        Pattern pattern = Pattern.compile(miTypeNumberTemplate);
        Matcher matcher = pattern.matcher(dto.getNumber());
        if (dto.getNumber() == null || !matcher.find()) {
            return "Некорректно указан регистрационный номер типа средства измерений" +
                    " в Федеральном информационном фонде по обеспечению единства измерений";
        }

        if (dto.getTitle() == null || dto.getTitle().isEmpty()){
            return "Пожалуйста заполните наименование типа средства измерений";
        }

        if (dto.getModifications().isEmpty()){
            return "Пожалуйста укажите не менее одной модификации типа средства измерений," +
                    " если модификации отсутствуют, введите единственную модифкацию," +
                    " соответствующую обозначению типа СИ или фразу \"Модификация отсутствует\"";
        }
        return "";
    }

    @Override
    public ResponseEntity<?> update(MiTypeFullDto miTypeDto){
        String errorMessage = checkMiTypeDtoComposition(miTypeDto);
        if (!errorMessage.isEmpty()) {
            log.info(errorMessage);
            return ResponseEntity.status(422).body(new ServiceMessage(errorMessage));
        }
        Optional<MiTypeInstruction> instructionOpt = miTypeInstructionRepository.findById(miTypeDto.getId());
        if (instructionOpt.isEmpty()){
            errorMessage = "Запись о типе СИ № " + miTypeDto.getNumber() + " не найдена";
            log.info(errorMessage);
            return ResponseEntity.status(404).body(new ServiceMessage(errorMessage));
        }
        MiTypeInstruction updateData = MiTypeDtoMapper.mapToEntity(miTypeDto);
        MiTypeInstruction instruction = instructionOpt.get();
        instruction.updateFrom(updateData);
        miTypeInstructionRepository.save(instruction);
        String okMessage ="Cведения о типе СИ " + miTypeDto.getNumber() +  " обновлены";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }

    @Override
    public ResponseEntity<?>delete(int id){
        Optional<MiType> miTypeOpt = miTypeRepository.findById(id);
        if (miTypeOpt.isEmpty()){
            String errorMessage = "Данные для удаления не найдены";
            log.info(errorMessage);
            return ResponseEntity.status(404).body(new ServiceMessage(errorMessage));
        }
        miTypeRepository.delete(miTypeOpt.get());
        String okMessage ="Запись о типе СИ №" + miTypeOpt.get().getNumber() + " успешно удалена";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }

    @Override
    public ResponseEntity<?> findById(int id) {
        Optional<MiTypeInstruction> instructionOpt = miTypeInstructionRepository.findById(id);
        if (instructionOpt.isPresent()) {
            MiTypeFullDto miTypeDto = MiTypeDtoMapper.mapToFullDto(instructionOpt.get());
            return ResponseEntity.ok(miTypeDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @Override
    public ResponseEntity<?> findByNumber(String number) {
        MiType type = miTypeRepository.findByNumber(number);
        if (type != null) {
            MiTypeDto miTypeDto = MiTypeDtoMapper.mapToDto(type);
            return ResponseEntity.ok(miTypeDto);
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
        Page<MiTypeDto> page =  miTypeRepository
                .findByNumberContainingOrTitleContainingOrNotationContaining(searchString.trim(),searchString.trim(),searchString.trim(), pageable)
                .map(MiTypeDtoMapper::mapToDto);
        return ResponseEntity.ok(page);
    }

    public ResponseEntity<?> findModifications(int miTypeId){
        Optional<MiType> typeOpt = miTypeRepository.findById(miTypeId);
        if (typeOpt.isPresent()) {
            List<MiTypeModification> modifications = miTypeModificationRepository.findByMiType(typeOpt.get());
            List<String> modificationNotations = modifications.stream().map(MiTypeModification::getNotation).toList();
            return ResponseEntity.ok(modificationNotations);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @Override
    public Page<MiTypeDto> findAll(Pageable pageable) {
        return miTypeRepository.findAll(pageable).map(MiTypeDtoMapper ::mapToDto);
    }

    @Override
    public List<MiTypeDto> findAll() {
        return miTypeRepository.findAll().stream().map(MiTypeDtoMapper ::mapToDto).toList();
    }

}
