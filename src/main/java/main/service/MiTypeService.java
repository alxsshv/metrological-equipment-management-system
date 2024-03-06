package main.service;

import main.dto.MiTypeDto;
import main.dto.mappers.MiTypeDtoMapper;
import main.model.MiType;
import main.repository.MiTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class MiTypeService {
    public static final Logger log = LoggerFactory.getLogger(MiTypeService.class);
    private final MiTypeRepository miTypeRepository;

    public MiTypeService(MiTypeRepository miTypeRepository) {
        this.miTypeRepository = miTypeRepository;
    }


    public ResponseEntity<?> save(MiTypeDto miTypeDto) {
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
        miTypeRepository.save(MiTypeDtoMapper.mapToEntity(miTypeDto));
        String okMessage = "Запись о типе СИ № " + miTypeDto.getNumber() + " успешно добавлена";
        log.info(okMessage);
        return ResponseEntity.ok(
                new ServiceMessage(okMessage));
    }
    private String checkMiTypeDtoComposition(MiTypeDto dto){
        String miTypeNumberTemplate = "[0-9]{5}-[0-9]{2}";
        Pattern pattern = Pattern.compile(miTypeNumberTemplate);
        Matcher matcher = pattern.matcher(dto.getNumber());
        if (dto.getNumber() == null || !matcher.find()) {
            return "Некорректно указан регистрационный номер типа средства измерений" +
                    " в Федеральном информационном фонде по обеспечению единства измерений";
        }
        if (dto.getNotation() == null || dto.getNotation().isEmpty()){
            return "Пожалуйста заполните обозначение типа средства измерений";
        }
        if (dto.getTitle() == null || dto.getTitle().isEmpty()){
            return "Пожалуйста заполните наименование типа средства измерений";
        }
        if (dto.getModifications().isEmpty()){
            return "Пожалуйста укажите не менее одной модификации типа средства измерений";
        }
        return "";
    }


    public ResponseEntity<?> update(MiTypeDto miTypeDto){
        String errorMessage = checkMiTypeDtoComposition(miTypeDto);
        if (!errorMessage.isEmpty()) {
            log.info(errorMessage);
            return ResponseEntity.status(422).body(new ServiceMessage(errorMessage));
        }

        Optional<MiType> userOpt = miTypeRepository.findById(miTypeDto.getId());
        if (userOpt.isEmpty()){
            errorMessage = "Запись о типе СИ № " + miTypeDto.getNumber() + " не найдена";
            log.info(errorMessage);
            return ResponseEntity.status(404).body(new ServiceMessage(errorMessage));
        }
        MiType updatingMiTypeData = MiTypeDtoMapper.mapToEntity(miTypeDto);
        MiType miType = userOpt.get();
        miType.updateFrom(updatingMiTypeData);
        miTypeRepository.save(miType);
        String okMessage ="Cведения о типе СИ " + miType.getNumber() +  " обновлены";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }


    public ResponseEntity<?>delete(int id){
        Optional<MiType> miTypeOpt = miTypeRepository.findById(id);
        if (miTypeOpt.isEmpty()){
            String errorMessage = "Данные по id = "+ id +" не найдены";
            log.info(errorMessage);
            return ResponseEntity.status(404).body(new ServiceMessage(errorMessage));
        }
        miTypeRepository.delete(miTypeOpt.get());
        String okMessage ="Запись о типе СИ №" + miTypeOpt.get().getNumber() + "успешно удалена";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }


    public ResponseEntity<?> findById(int id) {
        Optional<MiType> miTypeOpt = miTypeRepository.findById(id);
        if (miTypeOpt.isPresent()) {
            MiTypeDto miTypeDto = MiTypeDtoMapper.mapToDto(miTypeOpt.get());
            return ResponseEntity.ok(miTypeDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> findBySearchString(String searchString, Pageable pageable) {
        if (searchString == null || searchString.isEmpty()){
            String errorMessage = "Поле для поиска не может быть пустым";
            log.info(errorMessage);
            return ResponseEntity.status(400).body(new ServiceMessage(errorMessage));
        }
        Page<MiTypeDto> page =  miTypeRepository
                .findByNumberOrTitleOrNotationContaining(searchString.trim(),searchString.trim(),searchString.trim(), pageable)
                .map(MiTypeDtoMapper::mapToDto);
        return ResponseEntity.ok(page);
    }

    public Page<MiTypeDto> findAll(Pageable pageable) {
        return miTypeRepository.findAll(pageable).map(MiTypeDtoMapper ::mapToDto);
    }

    public List<MiTypeDto> findAll() {
        return miTypeRepository.findAll().stream().map(MiTypeDtoMapper ::mapToDto).toList();
    }

}
