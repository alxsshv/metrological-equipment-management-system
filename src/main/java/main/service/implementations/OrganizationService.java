package main.service.implementations;


import main.dto.rest.OrganizationDto;
import main.dto.rest.mappers.OrganizationDtoMapper;
import main.model.Organization;
import main.repository.OrganizationRepository;
import main.service.ServiceMessage;
import main.service.interfaces.IOrganizationService;
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
public class OrganizationService implements IOrganizationService {
    public static final Logger log = LoggerFactory.getLogger(OrganizationService.class);
    private final OrganizationRepository organizationRepository;

    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public ResponseEntity<?> save(OrganizationDto organizationDto){
        String errorMessage = checkOrganisationDtoComposition(organizationDto);
        if (!errorMessage.isEmpty()) {
            log.info(errorMessage);
            return ResponseEntity.status(422).body(
                    new ServiceMessage(errorMessage));
        }
        Organization organizationFromDb = organizationRepository.findByNotation(organizationDto.getNotation());
        if (organizationFromDb != null){
            errorMessage = "Запись об организации " + organizationDto.getNotation() + " уже существует";
            log.info(errorMessage);
            return ResponseEntity.status(422).body(
                    new ServiceMessage(errorMessage));
        }
        Organization organization = OrganizationDtoMapper.mapToEntity(organizationDto);
        organization.setCreationDateTime(LocalDateTime.now());
        organizationRepository.save(organization);
        String okMessage = "Запись об организации " + organizationDto.getNotation() + " успешно добавлена";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }

    private String checkOrganisationDtoComposition(OrganizationDto organizationDto){
        if (organizationDto.getTitle() == null || organizationDto.getTitle().isEmpty()) {
            return "Пожалуйста заполните полное наименование организации";
        }

        if (organizationDto.getNotation() == null || organizationDto.getNotation().isEmpty()){
            return "Пожалуйста заполните сокращенное наименование организации";
        }

        return "";
    }

    @Override
    public ResponseEntity<?> update(OrganizationDto organizationDto){
        String errorMessage = checkOrganisationDtoComposition(organizationDto);
        if (!errorMessage.isEmpty()) {
            log.info(errorMessage);
            return ResponseEntity.status(422).body(new ServiceMessage(errorMessage));
        }
        Optional<Organization> instructionOpt = organizationRepository.findById(organizationDto.getId());
        if (instructionOpt.isEmpty()){
            errorMessage = "Запись об организации " + organizationDto.getNotation() + " не найдена";
            log.info(errorMessage);
            return ResponseEntity.status(404).body(new ServiceMessage(errorMessage));
        }
        Organization updateData = OrganizationDtoMapper.mapToEntity(organizationDto);
        Organization organization = instructionOpt.get();
        organization.updateFrom(updateData);
        organizationRepository.save(organization);
        String okMessage ="Cведения об организации " + organization.getNotation() +  " обновлены";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }

    @Override
    public ResponseEntity<?>delete(long id){
        Optional<Organization> organizationOpt = organizationRepository.findById(id);
        if (organizationOpt.isEmpty()){
            String errorMessage = "Данные для удаления не найдены";
            log.info(errorMessage);
            return ResponseEntity.status(404).body(new ServiceMessage(errorMessage));
        }
        organizationRepository.delete(organizationOpt.get());
        String okMessage ="Запись об организации"  + organizationOpt.get().getNotation() + " успешно удалена";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }

    @Override
    public ResponseEntity<?> findById(long id) {
        Optional<Organization> instructionOpt = organizationRepository.findById(id);
        if (instructionOpt.isPresent()) {
            OrganizationDto organizationDto = OrganizationDtoMapper.mapToDto(instructionOpt.get());
            return ResponseEntity.ok(organizationDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<?> findBySearchString(String searchString) {
        if (searchString == null || searchString.isEmpty()){
            String errorMessage = "Поле для поиска не может быть пустым";
            log.info(errorMessage);
            return ResponseEntity.status(400).body(new ServiceMessage(errorMessage));
        }
        List<OrganizationDto> organizationDtos =  organizationRepository
                .findByTitleContainingOrNotationContaining(searchString.trim(),searchString.trim())
                .stream().map(OrganizationDtoMapper::mapToDto).toList();
        return ResponseEntity.ok(organizationDtos);
    }

    @Override
    public ResponseEntity<?> findBySearchStringWithPages (String searchString, Pageable pageable) {
        if (searchString == null || searchString.isEmpty()){
            String errorMessage = "Поле для поиска не может быть пустым";
            log.info(errorMessage);
            return ResponseEntity.status(400).body(new ServiceMessage(errorMessage));
        }
        Page<OrganizationDto> organizationDtos =  organizationRepository
                .findByTitleContainingOrNotationContaining(searchString.trim(),searchString.trim(), pageable)
                .map(OrganizationDtoMapper::mapToDto);
        return ResponseEntity.ok(organizationDtos);
    }

    @Override
    public Page<OrganizationDto> findAll(Pageable pageable) {
        return organizationRepository.findAll(pageable).map(OrganizationDtoMapper ::mapToDto);
    }

    @Override
    public List<OrganizationDto> findAll() {
        return organizationRepository.findAll().stream().map(OrganizationDtoMapper ::mapToDto).toList();
    }

}
