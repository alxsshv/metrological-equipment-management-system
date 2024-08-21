package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import main.config.AppDefaults;
import main.dto.rest.*;
import main.dto.rest.mappers.MiDetailsDtoMapper;
import main.dto.rest.mappers.OrganizationDtoMapper;
import main.exception.DtoCompositionException;
import main.model.*;
import main.repository.MiDetailsRepository;
import main.service.Category;
import main.service.interfaces.*;
import main.service.validators.MiAlreadyExist;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.Optional;
@AllArgsConstructor
@Service
@Validated
public class MiDetailsServiceImpl implements MiDetailsService {
    public static final Logger log = LoggerFactory.getLogger(MiDetailsServiceImpl.class);
    private final MiDetailsRepository miDetailsRepository;
    private final MeasurementInstrumentService measurementInstrumentService;
    private final MiTypeDetailsService miTypeDetailsService;
    private final OrganizationService organizationService;
    private final FileServiceImpl fileService;
    private final ModelMapper modelMapper = new ModelMapper();



    public void save(@MiAlreadyExist @Valid MiDetailsDto miDetailsDto, MultipartFile[] files, String[] descriptions) throws IOException {
            setDefaultsDtoFieldsIfEmpty(miDetailsDto);
            checkMeasurementInstrumentDtoComposition(miDetailsDto.getMiFullDto());
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            MiDetails miDetails = MiDetailsDtoMapper.mapToEntity(miDetailsDto);
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
            miDetailsDto.setStatus(modelMapper.map(AppDefaults.getMiStatusWorkingTool(), MiStatusDto.class));
        }
        if (miDetailsDto.getMeasCategory() == null) {
            miDetailsDto.setMeasCategory(modelMapper.map(AppDefaults.getDefaultMeasCategory(), MeasCategoryDto.class));
        }
        setDefaultMiOwnerIfEmpty(miDetailsDto.getMiFullDto());
    }

    private void setDefaultMiOwnerIfEmpty(@Valid MiFullDto miFullDto) throws DtoCompositionException {
        if (miFullDto.getOwner() == null){
            miFullDto.setOwner(OrganizationDtoMapper.mapToDto(AppDefaults.getDefaultOrganization()));
        }
    }

    private void checkMeasurementInstrumentDtoComposition(@Valid MiFullDto dto) throws DtoCompositionException {
        MiTypeDetails miTypeFromDb = miTypeDetailsService.getById(dto.getMiType().getId());
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


    private void uploadFilesIfFilesExist(MultipartFile[] files, String[] descriptions, Long categoryId) throws IOException {
        if (files.length > 0) {
            fileService.uploadAllFiles(files, descriptions, Category.MEASUREMENT_INSTRUMENT, categoryId);
        }
    }

    public MiDetailsDto findById(long id) {
        MiDetails miDetails = getById(id);
        return MiDetailsDtoMapper.mapToDto(miDetails);
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
            MiDetails updatingMiDetailsData = MiDetailsDtoMapper.mapToEntity(miDetailsDto);
            miDetailsFromDb.updateFrom(updatingMiDetailsData);
            miDetailsRepository.save(miDetailsFromDb);
    }

    public void delete(long id) {
            try {
                getById(id);
                fileService.deleteAllFiles(Category.MEASUREMENT_INSTRUMENT, id);
                miDetailsRepository.deleteById(id);
                measurementInstrumentService.deleteById(id);
            } catch (IOException ex) {
                String errorMessage = "Ошибка доступа к файлу";
                log.error("{}:{}", errorMessage, ex.getMessage());
                throw new RuntimeException(errorMessage + ":" + ex.getMessage());
            }
        }
}
