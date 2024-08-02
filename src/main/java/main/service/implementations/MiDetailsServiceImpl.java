package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import main.config.AppDefaults;
import main.dto.rest.*;
import main.dto.rest.mappers.MiDetailsMapper;
import main.dto.rest.mappers.OrganizationDtoMapper;
import main.exception.DtoCompositionException;
import main.exception.EntityAlreadyExistException;
import main.model.*;
import main.repository.MeasurementInstrumentRepository;
import main.repository.MiDetailsRepository;
import main.service.Category;
import main.service.interfaces.MiDetailsService;
import main.service.interfaces.MiTypeService;
import main.service.interfaces.OrganizationService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.Optional;
@AllArgsConstructor
@Service
public class MiDetailsServiceImpl implements MiDetailsService {
    public static final Logger log = LoggerFactory.getLogger(MiDetailsServiceImpl.class);
    private final MiDetailsRepository miDetailsRepository;
    //TODO: заменить репозиторий сервисом
    private final MeasurementInstrumentRepository measurementInstrumentRepository;
    private final MiTypeService miTypeService;

    private final OrganizationService organizationService;
    private final FileServiceImpl fileService;
    private final ModelMapper modelMapper = new ModelMapper();



    public void save(MiDetailsDto miDetailsDto, MultipartFile[] files, String[] descriptions) throws IOException {
            setDefaultsDtoFieldsIfEmpty(miDetailsDto);
            checkMeasurementInstrumentDtoComposition(miDetailsDto.getMiFullDto());
            validateIfEntityAlreadyExist(miDetailsDto.getMiFullDto());
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            MiDetails miDetails = MiDetailsMapper.mapToEntity(miDetailsDto);
            miDetails.getMi().setTitle(miDetails.getMi().getMiType().getMiTitleTemplate());
            MiDetails savedMiDetails = miDetailsRepository.save(miDetails);
            uploadFilesIfFilesExist(files, descriptions, savedMiDetails.getId());
    }


    private void setDefaultsDtoFieldsIfEmpty(MiDetailsDto miDetailsDto){
        if (miDetailsDto.getCondition() == null) {
            miDetailsDto.setCondition(modelMapper.map(AppDefaults.getDefaultMiCondition(), MiConditionDto.class));
        }
        if (miDetailsDto.getDepartment() == null) {
            miDetailsDto.setDepartment(modelMapper.map(AppDefaults.getDefaultDepartment(), DepartmentDto.class));
        }
        if (miDetailsDto.getStatus() == null) {
            miDetailsDto.setStatus(modelMapper.map(AppDefaults.getDefaultMiStatus(), MiStatusDto.class));
        }
        if (miDetailsDto.getMeasCategory() == null) {
            miDetailsDto.setMeasCategory(modelMapper.map(AppDefaults.getDefaultMeasCategory(), MeasCategoryDto.class));
        }
        setDefaultMiOwnerIfEmpty(miDetailsDto.getMiFullDto());
    }

    private void setDefaultMiOwnerIfEmpty(MiFullDto miFullDto) throws DtoCompositionException {
        if (miFullDto.getOwner() == null){
            miFullDto.setOwner(OrganizationDtoMapper.mapToDto(AppDefaults.getDefaultOrganization()));
        }
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
        MiType miTypeFromDb = miTypeService.getInstructionById(dto.getMiType().getId()).getMiType();
        if (miTypeFromDb == null){
            throw new DtoCompositionException("Выбранный тип средства измерений отсутствует в базе данных пожалуйста выберите имеющийся тип" +
                    " средства измерений  или добавьте требуемый тип средства измерений в базу");
        }
        Organization ownerFromDb = organizationService.getOrganizationById(dto.getOwner().getId());
        if (ownerFromDb == null){
            throw new DtoCompositionException("Указанная организация-владелец отсутствует в базе данных, пожалуйста проверьте правильность выбора " +
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


    public MiDetailsDto findById(long id) {
        MiDetails miDetails = getById(id);
        return MiDetailsMapper.mapToDto(miDetails);
    }

    public MiDetails getById(long id){
        Optional<MiDetails> miDetailsOpt = miDetailsRepository.findById(id);
        if (miDetailsOpt.isEmpty()){
            throw new EntityNotFoundException("Средство измерений № "+ id +" не найдено");
        }
        return miDetailsOpt.get();
    }



    public void update(MiDetailsDto miDetailsDto) {
            checkMeasurementInstrumentDtoComposition(miDetailsDto.getMiFullDto());
            MiDetails miDetailsFromDb = getById(miDetailsDto.getId());
            MiDetails updatingMiDetailsData = modelMapper.map(miDetailsDto, MiDetails.class);
            miDetailsFromDb.updateFrom(updatingMiDetailsData);
            miDetailsRepository.save(miDetailsFromDb);
    }


    public void delete(long id) {
            try {
                getById(id);
                fileService.deleteAllFiles(Category.MEASUREMENT_INSTRUMENT, id);
                miDetailsRepository.deleteById(id);
                measurementInstrumentRepository.deleteById(id);
            } catch (IOException ex) {
                String errorMessage = "Ошибка доступа к файлу";
                log.error("{}:{}", errorMessage, ex.getMessage());
                throw new RuntimeException(errorMessage + ":" + ex.getMessage());
            }
        }
}
