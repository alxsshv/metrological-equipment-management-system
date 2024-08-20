package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import main.dto.rest.MiTypeDetailsDto;
import main.dto.rest.mappers.MiTypeDetailsDtoMapper;
import main.exception.DeleteOperationException;
import main.model.MiTypeDetails;
import main.model.MiTypeModification;
import main.repository.MiTypeDetailsRepository;
import main.repository.MiTypeModificationRepository;
import main.service.Category;
import main.service.interfaces.MiTypeDetailsService;
import main.service.interfaces.MiTypeService;
import main.service.validators.MiTypeAlreadyExist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@Service
@Validated
@AllArgsConstructor
public class MiTypeDetailsServiceImpl implements MiTypeDetailsService {
    public static final Logger log = LoggerFactory.getLogger(MiTypeDetailsServiceImpl.class);
    private final MiTypeService miTypeService;
    private final MiTypeDetailsRepository miTypeDetailsRepository;
    private final MiTypeModificationRepository miTypeModificationRepository;
    private final FileServiceImpl fileService;


    @Override
    public void save(@MiTypeAlreadyExist @Valid MiTypeDetailsDto miTypeDetailsDto, MultipartFile[] files, String[] descriptions) throws IOException {
            MiTypeDetails miTypeDetails = MiTypeDetailsDtoMapper.mapToEntity(miTypeDetailsDto);
            MiTypeDetails savedMiTypeDetails = miTypeDetailsRepository.save(miTypeDetails);
            uploadFilesIfFilesExist(files, descriptions, savedMiTypeDetails.getId());
    }


    private void uploadFilesIfFilesExist(MultipartFile[] files, String[] descriptions, Long categoryId) throws IOException {
        if (files.length > 0) {
            fileService.uploadAllFiles(files, descriptions, Category.MI_TYPE, categoryId);
        }
    }

    @Override
    public MiTypeDetailsDto findById(long id) {
        MiTypeDetails miTypeDetails = getById(id);
        return MiTypeDetailsDtoMapper.mapToDto(miTypeDetails);
    }

    public MiTypeDetails getById(long id){
        Optional<MiTypeDetails> miTypeDetailsOpt = miTypeDetailsRepository.findById(id);
        if(miTypeDetailsOpt.isEmpty()){
            throw new EntityNotFoundException("Запись о типе СИ № " + id + " не найдена");
        }
        return miTypeDetailsOpt.get();
    }


    @Override
    public List<String> findModifications(long miTypeId){
        MiTypeDetails miTypeDetails = getById(miTypeId);
        List<MiTypeModification> modifications = miTypeModificationRepository.findByMiTypeDetails(miTypeDetails);
        return modifications.stream().map(MiTypeModification::getNotation).toList();
    }

    @Override
    public List<String> findModificationsByMiTypeDetailsIdAndSearchString(long miTypeId, String searchString){
        MiTypeDetails miTypeDetails = getById(miTypeId);
        List<MiTypeModification> foundModifications = miTypeModificationRepository.findByMiTypeDetailsAndNotationIgnoreCaseContaining(miTypeDetails, searchString);
        return foundModifications.stream().map(MiTypeModification::getNotation).toList();
    }


    @Override
    public void update(@Valid MiTypeDetailsDto miTypeDetailsDto){
            MiTypeDetails miTypeDetailsFromDb = getById(miTypeDetailsDto.getId());
            MiTypeDetails updateData = MiTypeDetailsDtoMapper.mapToEntity(miTypeDetailsDto);
            miTypeDetailsFromDb.updateFrom(updateData);
            miTypeDetailsRepository.save(miTypeDetailsFromDb);
    }

    @Override
    public void delete(long id) throws IOException {
        try {
            fileService.deleteAllFiles(Category.MI_TYPE, id);
            miTypeDetailsRepository.deleteById(id);
            miTypeService.deleteById(id);
        } catch (DataIntegrityViolationException ex){
            log.warn(ex.getMessage());
            String errorMessage = "Удаление типа СИ не возможно, т.к. в базе пристуствуют средства измерений, относящиеся к данному типу СИ";
            throw new DeleteOperationException(errorMessage);
        }
    }



}
