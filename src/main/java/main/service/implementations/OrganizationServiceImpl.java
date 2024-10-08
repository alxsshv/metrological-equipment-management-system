package main.service.implementations;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import main.dto.rest.OrganizationDto;
import main.exception.EntityAlreadyExistException;
import main.model.Organization;
import main.repository.OrganizationRepository;
import main.service.interfaces.OrganizationService;
import main.service.validators.OrganizationAlreadyExist;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Validated
public class OrganizationServiceImpl implements OrganizationService {
    public static final Logger log = LoggerFactory.getLogger(OrganizationServiceImpl.class);
    private final OrganizationRepository organizationRepository;
    private final ModelMapper modelMapper;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
        this.modelMapper = new ModelMapper();
    }

    @Override
    public void save(@OrganizationAlreadyExist @Valid OrganizationDto organizationDto) throws EntityAlreadyExistException{
            Organization organization = modelMapper.map(organizationDto, Organization.class);
            organizationRepository.save(organization);
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
    public List<OrganizationDto> findBySearchString(@NotBlank(message = "Поле для поиска не может быть пустым") String searchString) {
         return organizationRepository.findByTitleIgnoreCaseContainingOrNotationIgnoreCaseContaining(searchString.trim(),searchString.trim())
                .stream().map(organization -> modelMapper.map(organization, OrganizationDto.class)).toList();
    }



    @Override
    public Page<OrganizationDto> findBySearchString(@NotBlank(message = "Поле для поиска не может быть пустым") String searchString, Pageable pageable) {
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
    public void update(@Valid OrganizationDto organizationDto){
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
