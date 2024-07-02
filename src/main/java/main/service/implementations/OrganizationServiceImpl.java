package main.service.implementations;


import jakarta.persistence.EntityNotFoundException;
import main.dto.rest.OrganizationDto;
import main.dto.rest.mappers.OrganizationDtoMapper;
import main.exception.DtoCompositionException;
import main.exception.EntityAlreadyExistException;
import main.exception.ParameterNotValidException;
import main.model.Organization;
import main.repository.OrganizationRepository;
import main.service.ServiceMessage;
import main.service.interfaces.OrganizationService;
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
public class OrganizationServiceImpl implements OrganizationService {
    public static final Logger log = LoggerFactory.getLogger(OrganizationServiceImpl.class);
    private final OrganizationRepository organizationRepository;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public ResponseEntity<?> save(OrganizationDto organizationDto) {
        try {
            checkOrganisationDtoComposition(organizationDto);
            checkExistenceEntity(organizationDto.getNotation());
            Organization organization = OrganizationDtoMapper.mapToEntity(organizationDto);
            organization.setCreationDateTime(LocalDateTime.now());
            organizationRepository.save(organization);
            String okMessage = "Запись об организации " + organizationDto.getNotation() + " успешно добавлена";
            log.info(okMessage);
            return ResponseEntity.ok(new ServiceMessage(okMessage));
        } catch (EntityAlreadyExistException | DtoCompositionException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(400).body(
                    new ServiceMessage(ex.getMessage()));
        }
    }


    private void checkOrganisationDtoComposition(OrganizationDto organizationDto) throws DtoCompositionException {
        if (organizationDto.getTitle() == null || organizationDto.getTitle().isEmpty()) {
            throw new DtoCompositionException("Пожалуйста заполните полное наименование организации");
        }
        if (organizationDto.getNotation() == null || organizationDto.getNotation().isEmpty()){
            throw new DtoCompositionException("Пожалуйста заполните сокращенное наименование организации");
        }
    }

    private void checkExistenceEntity(String orgNotation) throws EntityAlreadyExistException {
        Organization organizationFromDb = organizationRepository.findByNotation(orgNotation);
        if (organizationFromDb != null){
            throw new EntityAlreadyExistException("Запись об организации "+ orgNotation + " уже существует");
        }
    }

    @Override
    public ResponseEntity<?> findById(long id) {
        try {
            Organization organization = getOrganizationById(id);
            OrganizationDto organizationDto = OrganizationDtoMapper.mapToDto(organization);
            return ResponseEntity.ok(organizationDto);
        } catch (EntityNotFoundException ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(404).body(new ServiceMessage(ex.getMessage()));
        }
    }

    @Override
    public Organization getOrganizationById(long id){
        Optional<Organization> instructionOpt = organizationRepository.findById(id);
        if (instructionOpt.isEmpty()){
            throw new EntityNotFoundException("Информация об организации № " + id + " не найдена");
        }
        return instructionOpt.get();
    }

    @Override
    public ResponseEntity<?> findBySearchString(String searchString) {
        if (searchString == null || searchString.isEmpty()){
            String errorMessage = "Поле для поиска не может быть пустым";
            log.error(errorMessage);
            return ResponseEntity.status(400).body(new ServiceMessage(errorMessage));
        }
        List<OrganizationDto> organizationDtos =  organizationRepository
                .findByTitleIgnoreCaseContainingOrNotationIgnoreCaseContaining(searchString.trim(),searchString.trim())
                .stream().map(OrganizationDtoMapper::mapToDto).toList();
        return ResponseEntity.ok(organizationDtos);
    }

    @Override
    public ResponseEntity<?> findBySearchString(String searchString, Pageable pageable) {
        try {
            validateSearchString(searchString);
            Page<OrganizationDto> organizationDtos = organizationRepository
                    .findByTitleIgnoreCaseContainingOrNotationIgnoreCaseContaining(searchString.trim(), searchString.trim(), pageable)
                    .map(OrganizationDtoMapper::mapToDto);
            return ResponseEntity.ok(organizationDtos);
        } catch (ParameterNotValidException ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(400).body(new ServiceMessage(ex.getMessage()));
        }

    }

    private void validateSearchString(String searchString) throws ParameterNotValidException {
        if (searchString == null || searchString.isEmpty()){
            throw new ParameterNotValidException("Поле для поиска не может быть пустым");
        }
    }

    @Override
    public Page<OrganizationDto> findAll(Pageable pageable) {
        return organizationRepository.findAll(pageable).map(OrganizationDtoMapper ::mapToDto);
    }

    @Override
    public List<OrganizationDto> findAll() {
        return organizationRepository.findAll().stream().map(OrganizationDtoMapper ::mapToDto).toList();
    }

    @Override
    public ResponseEntity<?> update(OrganizationDto organizationDto){
        try {
            checkOrganisationDtoComposition(organizationDto);
            Organization organization = getOrganizationById(organizationDto.getId());
            Organization updateData = OrganizationDtoMapper.mapToEntity(organizationDto);
            organization.updateFrom(updateData);
            organizationRepository.save(organization);
            String okMessage = "Cведения об организации " + organization.getNotation() + " обновлены";
            log.info(okMessage);
            return ResponseEntity.ok(new ServiceMessage(okMessage));
        } catch (EntityNotFoundException | DtoCompositionException ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(400).body(new ServiceMessage(ex.getMessage()));
        }

    }

    @Override
    public ResponseEntity<?>delete(long id){
        organizationRepository.deleteById(id);
        String okMessage ="Запись об организации"  + id + " успешно удалена";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }



}
