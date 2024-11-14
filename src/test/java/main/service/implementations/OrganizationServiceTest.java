package main.service.implementations;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.*;
import main.dto.rest.OrganizationDto;
import main.model.Organization;
import main.repository.OrganizationRepository;

import main.service.validators.OrganizationAlreadyExistValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class OrganizationServiceTest {
    private final OrganizationRepository organizationRepository = Mockito.mock(OrganizationRepository.class);
    private final OrganizationServiceImpl organizationService = new OrganizationServiceImpl(organizationRepository);
    private final Pageable pageable = PageRequest.of(0,10, Sort.by(Sort.Direction.ASC,"notation"));
    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();
    @Autowired
    ConstraintValidatorContext constraintValidatorContext;

    @Test
    @DisplayName("Test save if organization already exist")
    public void testSaveIfOrganizationAlreadyExist() {
        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.setNotation("Организация");
        when(organizationRepository.findByNotation(organizationDto.getNotation())).thenReturn(new Organization());
        boolean notExist = new OrganizationAlreadyExistValidator(organizationRepository).isValid(organizationDto, constraintValidatorContext);
        assertFalse(notExist);
    }

    @Test
    @DisplayName("Test save if organization title is empty")
    public void testSaveIfOrganisationTitleIsEmpty() {
        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.setTitle("");
        organizationDto.setNotation("Организация");
        Set<ConstraintViolation<OrganizationDto>> violations = validator.validate(organizationDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Test save if organization title is null")
    public void testSaveIfOrganisationTitleIsNull() {
        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.setTitle(null);
        organizationDto.setNotation("Организация");
        Set<ConstraintViolation<OrganizationDto>> violations = validator.validate(organizationDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Test save if organization notation is empty")
    public void testSaveIfOrganisationNotationIsEmpty() {
        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.setTitle("Организация");
        organizationDto.setNotation("");
        Set<ConstraintViolation<OrganizationDto>> violations = validator.validate(organizationDto);
        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Test save if add new organization")
    public void testSaveIfAddNewOrganization(){
        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.setTitle("ООО Организация");
        organizationDto.setNotation("Организация");
        Set<ConstraintViolation<OrganizationDto>> violations = validator.validate(organizationDto);
        assertTrue(violations.isEmpty());
        organizationService.save(organizationDto);
        verify(organizationRepository, times(1)).save(any(Organization.class));
    }

    @Test
    @DisplayName("Test getOrganizationById if organization is found")
    public void testGetOrganizationByIdIfOrganizationIsFound(){
        long id = 5L;
        Organization organization = new Organization();
        organization.setId(id);
        when(organizationRepository.findById(id)).thenReturn(Optional.of(organization));
        Organization actualOrganization = organizationService.getOrganizationById(id);
        assertEquals(id,actualOrganization.getId());
    }

    @Test
    @DisplayName("Test findById if organization is found")
    public void TestFindByIdIfOrganizationIsFound(){
        long id = 1L;
        Organization organization = new Organization();
        organization.setId(id);
        when(organizationRepository.findById(id)).thenReturn(Optional.of(organization));
        OrganizationDto organizationDto = organizationService.findById(id);
        assertEquals(id, organizationDto.getId());
    }

    @Test
    @DisplayName("Test getOrganizationById if organization is not found")
    public void testGetOrganizationByIdIfOrganizationIsNotFound(){
        long id = 5L;
        Organization organization = new Organization();
        organization.setId(id);
        when(organizationRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()->
            organizationService.getOrganizationById(id));
    }


    @Test
    @DisplayName("Test findBySearchString if searchString is not empty")
    public void testFindBySearchStringIfSearchStringIsEmpty(){
        String searchString = "Органиация";
        when(organizationRepository.findByTitleIgnoreCaseContainingOrNotationIgnoreCaseContaining(searchString, searchString)).thenReturn(List.of(new Organization()));
        List<OrganizationDto> organizations = organizationService.findBySearchString(searchString);
        assertEquals(1, organizations.size());
    }

    @Test
    @DisplayName("Test findBySearchString (pageable)")
    public void testFindBySearchStringPageable(){
        String searchString = "Организация";
        Page<Organization> page = new PageImpl<>(List.of(new Organization(),  new Organization()), pageable, 1);
        when(organizationRepository.findByTitleIgnoreCaseContainingOrNotationIgnoreCaseContaining(searchString, searchString, pageable)).thenReturn(page);
        Page<OrganizationDto> dtoPage = organizationService.findBySearchString(searchString, pageable);
        assertEquals(page.getContent().size(),dtoPage.getContent().size());
    }

    @Test
    @DisplayName("Test findAll (pageable)")
    public void testFindAllPageable(){
        Page<Organization> page = new PageImpl<>(List.of(new Organization(), new Organization()), pageable, 1);
        when(organizationRepository.findAll(pageable)).thenReturn(page);
        Page<OrganizationDto> dtoPage = organizationService.findAll(pageable);
        assertEquals(page.getContent().size(), dtoPage.getContent().size());
    }

    @Test
    @DisplayName("Test findAll")
    public void testFindAll(){
        when(organizationRepository.findAll()).thenReturn(List.of(new Organization(), new Organization()));
        List<OrganizationDto> dtoList = organizationService.findAll();
        assertEquals(2,dtoList.size());
    }

    @Test
    @DisplayName("Test update if organization found")
    public void testUpdateIfOrganizationFound(){
        long id = 5L;
        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.setId(id);
        when(organizationRepository.findById(id)).thenReturn(Optional.of(new Organization()));
        organizationService.update(organizationDto);
        verify(organizationRepository, times(1)).save(any(Organization.class));
    }

    @Test
    @DisplayName("Test update if organization not found")
    public void testUpdateIfOrganizationNotFound(){
        long id = 5L;
        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.setId(id);
        when(organizationRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, ()-> organizationService.update(organizationDto));
        verify(organizationRepository, never()).save(any(Organization.class));
    }

    @Test
    @DisplayName("Test delete")
    public void testDelete(){
        long id = 5L;
        organizationService.delete(id);
        verify(organizationRepository,times(1)).deleteById(id);
    }





}

