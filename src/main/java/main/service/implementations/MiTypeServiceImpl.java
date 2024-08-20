package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotBlank;
import main.dto.rest.MiTypeDto;
import main.dto.rest.mappers.MiTypeDtoMapper;
import main.exception.EntityAlreadyExistException;
import main.model.MiType;
import main.repository.MiTypeDetailsRepository;
import main.repository.MiTypeModificationRepository;
import main.repository.MiTypeRepository;
import main.service.Category;
import main.service.interfaces.MiTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
@Validated
public class MiTypeServiceImpl implements MiTypeService {
    public static final Logger log = LoggerFactory.getLogger(MiTypeServiceImpl.class);
    private final MiTypeRepository miTypeRepository;
    private final FileServiceImpl fileService;

    public MiTypeServiceImpl(MiTypeRepository miTypeRepository,
                             MiTypeDetailsRepository miTypeInstructionRepository,
                             MiTypeModificationRepository miTypeModificationRepository,
                             FileServiceImpl fileService) {
        this.miTypeRepository = miTypeRepository;
        this.fileService = fileService;
    }


    private void validateIfEntityAlreadyExist(String miTypeNumber) throws EntityAlreadyExistException {
        MiType miTypeFromDb = miTypeRepository.findByNumber(miTypeNumber);
        if (miTypeFromDb != null){
            throw new EntityAlreadyExistException("Запись о типе СИ № " + miTypeNumber + " уже существует");
        }
    }

    private void uploadFilesIfFilesExist(MultipartFile[] files, String[] descriptions, Long categoryId) throws IOException {
        if (files.length > 0) {
            fileService.uploadAllFiles(files, descriptions, Category.MI_TYPE, categoryId);
        }
    }


    @Override
    public MiTypeDto findByNumber(@NotBlank(message = "Номер в ФИФ ОЕИ не может быть пустым") String number) {
        MiType type = miTypeRepository.findByNumber(number);
        if (type == null) {
            throw new EntityNotFoundException("Запись о типе СИ c № в ФИФ ОЕИ " + number + " не найдена");
        }
            return MiTypeDtoMapper.mapToDto(type);
    }

    @Override
    public Page<MiTypeDto> findBySearchString(@NotBlank(message = "Поле для поиска не может быть пустым") String searchString, Pageable pageable) {
            return miTypeRepository
                    .findByNumberContainingOrTitleIgnoreCaseContainingOrNotationIgnoreCaseContaining(searchString.trim(),searchString.trim(),searchString.trim(), pageable)
                    .map(MiTypeDtoMapper::mapToDto);
    }

    @Override
    public List<MiTypeDto> findBySearchString(
            @NotBlank(message = "Поле для поиска не может быть пустым") String searchString) {
            return miTypeRepository
                    .findByNumberContainingOrTitleIgnoreCaseContainingOrNotationIgnoreCaseContaining(searchString.trim(), searchString.trim(), searchString.trim()).stream()
                    .map(MiTypeDtoMapper::mapToDto).toList();
    }

    @Override
    public Page<MiTypeDto> findAll(Pageable pageable) {
        return miTypeRepository.findAll(pageable).map(MiTypeDtoMapper ::mapToDto);
    }

    @Override
    public List<MiTypeDto> findAll() {
        return miTypeRepository.findAll().stream().map(MiTypeDtoMapper ::mapToDto).toList();
    }

    @Override
    public void deleteById(long id) throws IOException {
            fileService.deleteAllFiles(Category.MI_TYPE, id);
            miTypeRepository.deleteById(id);

    }



}
