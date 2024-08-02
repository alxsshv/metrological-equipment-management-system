package main.service.implementations;


import jakarta.persistence.EntityNotFoundException;
import main.dto.rest.OrganizationDto;
import main.exception.DtoCompositionException;
import main.exception.EntityAlreadyExistException;
import main.exception.ParameterNotValidException;
import main.model.Organization;
import main.repository.OrganizationRepository;
import main.service.interfaces.OrganizationService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    public static final Logger log = LoggerFactory.getLogger(OrganizationServiceImpl.class);
    private final OrganizationRepository organizationRepository;
    private final ModelMapper modelMapper;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public void save(OrganizationDto organizationDto) throws EntityAlreadyExistException{
            checkOrganisationDtoComposition(organizationDto);
            validateIfEntityAlreadyExist(organizationDto.getNotation());
            Organization organization = modelMapper.map(organizationDto, Organization.class);
            organizationRepository.save(organization);
    }

    private void checkOrganisationDtoComposition (OrganizationDto organizationDto) throws DtoCompositionException {
        if (organizationDto.getTitle() == null || organizationDto.getTitle().isEmpty()) {
            throw new DtoCompositionException("Пожалуйста заполните полное наименование организации");
        }
        if (organizationDto.getNotation() == null || organizationDto.getNotation().isEmpty()){
            throw new DtoCompositionException("Пожалуйста заполните сокращенное наименование организации");
        }
    }

    private void validateIfEntityAlreadyExist(String orgNotation) throws EntityAlreadyExistException {
        Organization organizationFromDb = organizationRepository.findByNotation(orgNotation);
        if (organizationFromDb != null){
            throw new EntityAlreadyExistException("Запись об организации \""+ orgNotation + "\" уже существует");
        }
    }

    @Override
    public OrganizationDto findById(long id) {
            Organization organization = getOrganizationById(id);
            return modelMapper.map(organization, OrganizationDto.class);
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
    public List<OrganizationDto> findBySearchString(String searchString) {
            validateSearchString(searchString);
         return organizationRepository.findByTitleIgnoreCaseContainingOrNotationIgnoreCaseContaining(searchString.trim(),searchString.trim())
                .stream().map(organization -> modelMapper.map(organization, OrganizationDto.class)).toList();
    }

    private void validateSearchString(String searchString) {
        if (searchString == null || searchString.isEmpty()){
            throw  new ParameterNotValidException("Поле для поиска не может быть пустым");
        }
    }


    @Override
    public Page<OrganizationDto> findBySearchString(String searchString, Pageable pageable) {
            validateSearchString(searchString);
            return organizationRepository
                    .findByTitleIgnoreCaseContainingOrNotationIgnoreCaseContaining(searchString.trim(), searchString.trim(), pageable)
                    .map(organization -> modelMapper.map(organization, OrganizationDto.class));
    }


    @Override
    public Page<OrganizationDto> findAll(Pageable pageable) {
        return organizationRepository.findAll(pageable)
                .map(organization -> modelMapper.map(organization, OrganizationDto.class));
    }

    @Override
    public List<OrganizationDto> findAll() {
        return organizationRepository.findAll().stream()
                .map(organization -> modelMapper.map(organization, OrganizationDto.class)).toList();
    }

    @Override
    public void update(OrganizationDto organizationDto){
            checkOrganisationDtoComposition(organizationDto);
            Organization organization = getOrganizationById(organizationDto.getId());
            Organization updateData = modelMapper.map(organizationDto, Organization.class);
            organization.updateFrom(updateData);
            organizationRepository.save(organization);
    }

    @Override
    public void delete(long id){
        organizationRepository.deleteById(id);
    }



}
