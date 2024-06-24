package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import main.dto.rest.OrganizationDto;
import main.dto.rest.mappers.OrganizationDtoMapper;
import main.model.Organization;
import main.repository.OrganizationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static testutils.TestEntityGenerator.*;

public class OrganizationServiceTest {
    private final OrganizationRepository organizationRepository = Mockito.mock(OrganizationRepository.class);
    private final OrganizationService organizationService = new OrganizationService(organizationRepository);
    private final Pageable pageable = PageRequest.of(0,10, Sort.by(Sort.Direction.ASC,"notation"));

    @Test
    @DisplayName("Test save if added new organisation")
    public void testSaveIfAddedNewOrganisation() {
        OrganizationDto organizationDto = generateOrganisationDto(1L);
        Organization organization = OrganizationDtoMapper.mapToEntity(organizationDto);
        when(organizationRepository.findByNotation(organizationDto.getNotation())).thenReturn(null);
        when(organizationRepository.save(organization)).thenReturn(organization);
        ResponseEntity<?> responseEntity = organizationService.save(organizationDto);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
        verify(organizationRepository, times(1)).findByNotation(organizationDto.getNotation());
        verify(organizationRepository, times(1)).save(any(Organization.class));

    }

    @Test
    @DisplayName("Test save if organisation already exist")
    public void testSaveIfOrganizationAlreadyExist() {
        OrganizationDto organizationDto = generateOrganisationDto(1L);
        when(organizationRepository.findByNotation(organizationDto.getNotation())).thenReturn(new Organization());
        ResponseEntity<?> responseEntity = organizationService.save(organizationDto);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(organizationRepository, times(1)).findByNotation(organizationDto.getNotation());
        verify(organizationRepository, never()).save(any(Organization.class));
    }

    @Test
    @DisplayName("Test save if organisation title is empty")
    public void testSaveIfOrganizationTitleIsEmpty() {
        OrganizationDto organizationDto = generateOrganisationDto(1L);
        organizationDto.setTitle("");
        Organization organization = OrganizationDtoMapper.mapToEntity(organizationDto);
        when(organizationRepository.findByNotation(organizationDto.getNotation())).thenReturn(organization);
        when(organizationRepository.save(organization)).thenReturn(organization);
        ResponseEntity<?> responseEntity = organizationService.save(organizationDto);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(organizationRepository, never()).findByNotation(organizationDto.getNotation());
        verify(organizationRepository, never()).save(any(Organization.class));
    }

    @Test
    @DisplayName("Test save if organisation notation is empty")
    public void testSaveIfOrganizationNotationIsEmpty() {
        OrganizationDto organizationDto = generateOrganisationDto(1L);
        organizationDto.setNotation(null);
        Organization organization = OrganizationDtoMapper.mapToEntity(organizationDto);
        when(organizationRepository.findByNotation(organizationDto.getNotation())).thenReturn(organization);
        when(organizationRepository.save(organization)).thenReturn(organization);
        ResponseEntity<?> responseEntity = organizationService.save(organizationDto);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(organizationRepository, never()).findByNotation(organizationDto.getNotation());
        verify(organizationRepository, never()).save(any(Organization.class));
    }

    @Test
    @DisplayName("Test update if organization is found")
    public void testUpdateIfOrganisationIsFound() {
        Long organizationId = 1L;
        OrganizationDto organizationDto = generateOrganisationDto(organizationId);
        Organization organization = OrganizationDtoMapper.mapToEntity(organizationDto);
        when(organizationRepository.findById(organizationId)).thenReturn(Optional.of(organization));
        ResponseEntity<?> responseEntity = organizationService.update(organizationDto);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
        verify(organizationRepository, times(1)).save(any(Organization.class));
        verify(organizationRepository, times(1)).findById(organizationId);
    }

    @Test
    @DisplayName("Test update if organization not found")
    public void testUpdateIfOrganisationNotFound() {
        Long organizationId = 1L;
        OrganizationDto organizationDto = generateOrganisationDto(organizationId);
        when(organizationRepository.findById(organizationId)).thenReturn(Optional.empty());
        ResponseEntity<?> responseEntity = organizationService.update(organizationDto);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(organizationRepository, times(1)).findById(organizationId);
        verify(organizationRepository, never()).save(any(Organization.class));
    }

    @Test
    @DisplayName("Test update if organization title is empty")
    public void testUpdateIfOrganizationTitleIsEmpty() {
        Long organizationId = 1L;
        OrganizationDto organizationDto = generateOrganisationDto(organizationId);
        organizationDto.setTitle(null);
        Organization organization = OrganizationDtoMapper.mapToEntity(organizationDto);
        when(organizationRepository.findById(organizationId)).thenReturn(Optional.of(organization));
        ResponseEntity<?> responseEntity = organizationService.update(organizationDto);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(organizationRepository, never()).findById(organizationId);
        verify(organizationRepository, never()).save(organization);
    }

    @Test
    @DisplayName("Test update if organization notation is empty")
    public void testUpdateIfOrganizationNotationIsEmpty() {
        Long organizationId = 1L;
        OrganizationDto organizationDto = generateOrganisationDto(organizationId);
        organizationDto.setNotation("");
        Organization organization = OrganizationDtoMapper.mapToEntity(organizationDto);
        when(organizationRepository.findById(organizationId)).thenReturn(Optional.of(organization));
        ResponseEntity<?> responseEntity = organizationService.update(organizationDto);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(organizationRepository, never()).findById(organizationId);
        verify(organizationRepository, never()).save(organization);
    }

    @Test
    @DisplayName("Test delete")
    public void testDelete(){
        long organizationId = 1L;
        ResponseEntity<?> responseEntity = organizationService.delete(organizationId);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
        verify(organizationRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Test findById if organization is found")
    public void testFindByIdIfOrganizationIsFound(){
        long organizationId = 1L;
        Organization organization = generateOrganisation(organizationId);
        when(organizationRepository.findById(organizationId)).thenReturn(Optional.of(organization));
        ResponseEntity<?> responseEntity = organizationService.findById(organizationId);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
        verify(organizationRepository,times(1)).findById(organizationId);
    }

    @Test
    @DisplayName("Test findById if organization not found")
    public void testFindByIdIfOrganizationNotFound(){
        long organizationId = 1L;
        when(organizationRepository.findById(organizationId)).thenReturn(Optional.empty());
        ResponseEntity<?> responseEntity = organizationService.findById(organizationId);
        assertEquals("404 NOT_FOUND", responseEntity.getStatusCode().toString());
        verify(organizationRepository,times(1)).findById(organizationId);
    }

    @Test
    @DisplayName("test getOrganizationById if entity not found")
    public void testGetOrganizationByIdIfEntityNotFound() throws EntityNotFoundException {
        long organisationId = 1L;
        when(organizationRepository.findById(organisationId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class,
                ()-> organizationService.getOrganizationById(organisationId)) ;
        assertEquals("Информация об организации № 1 не найдена",exception.getMessage());
        verify(organizationRepository,times(1)).findById(organisationId);
    }

    @Test
    @DisplayName("test getOrganizationById if entity found")
    public void testGetOrganizationByIdIfEntityFound() throws EntityNotFoundException {
        long organisationId = 1L;
        Organization expectedOrganization = generateOrganisation(organisationId);
        when(organizationRepository.findById(organisationId)).thenReturn(Optional.of(expectedOrganization));
        Organization actualOrganization = organizationService.getOrganizationById(organisationId);
        assertEquals(expectedOrganization, actualOrganization);
        verify(organizationRepository,times(1)).findById(organisationId);
    }


    @Test
    @DisplayName("Test findBySearchString if search string is empty")
    public void testFindBySearchStringIfSearchStringIsEmpty(){
        String searchString = "";
        ResponseEntity<?> responseEntity = organizationService.findBySearchString(searchString);
        assertEquals("400 BAD_REQUEST",responseEntity.getStatusCode().toString());
        verify(organizationRepository,never()).findByTitleContainingOrNotationContaining(searchString,searchString);
    }

    @Test
    @DisplayName("Test findBySearchString if search string is not empty")
    public void testFindBySearchStringIfSearchStringIsNotEmpty(){
        String searchString = "АО\"Организация\"";
        List<Organization> organizations = new ArrayList<>();
        when(organizationRepository.findByTitleContainingOrNotationContaining(searchString, searchString))
                .thenReturn(organizations);
        ResponseEntity<?> responseEntity = organizationService.findBySearchString(searchString);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
        verify(organizationRepository,times(1))
                .findByTitleContainingOrNotationContaining(searchString,searchString);
    }

    @Test
    @DisplayName("Test findBySearchStringWithPages if search string is empty")
    public void testFindBySearchStringWithPagesIfSearchStringIsEmpty(){
        String searchString = "";
        ResponseEntity<?> responseEntity = organizationService.findBySearchString(searchString, pageable);
        assertEquals("400 BAD_REQUEST",responseEntity.getStatusCode().toString());
        verify(organizationRepository,never()).findByTitleContainingOrNotationContaining(searchString,searchString,pageable);
    }

    @Test
    @DisplayName("Test findBySearchString with pages if search string is not empty")
    public void testFindBySearchStringWithPagesIfSearchStringIsNotEmpty(){
        String searchString = "АО\"Организация\"";
        Page<Organization> organizationsPage = Page.empty();
        when(organizationRepository.findByTitleContainingOrNotationContaining(searchString, searchString, pageable))
                .thenReturn(organizationsPage);
        ResponseEntity<?> responseEntity = organizationService.findBySearchString(searchString,pageable);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
        verify(organizationRepository,times(1))
                .findByTitleContainingOrNotationContaining(searchString,searchString, pageable);
    }

    @Test
    @DisplayName("Test findAll with pages")
    public void testFindAllWithPages(){
        int totalPages = 1;
        Organization organization = generateOrganisation(1L);
        List<Organization> organizations = List.of(organization);
        Page<Organization> organizationPage = new PageImpl<>(organizations,pageable,totalPages);
        when(organizationRepository.findAll(pageable)).thenReturn(organizationPage);
        Page<OrganizationDto> organizationDtos = organizationService.findAll(pageable);
        assertEquals(organizationPage.getContent().size(), organizationDtos.getContent().size());
        verify(organizationRepository,times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Test findAll without pages")
    public void testFindAllWithoutPages(){
        Organization organization = generateOrganisation(1L);
        List<Organization> organizations = List.of(organization);
        when(organizationRepository.findAll()).thenReturn(organizations);
        List<OrganizationDto> organizationDtos = organizationService.findAll();
        assertEquals(organizations.size(), organizationDtos.size());
        verify(organizationRepository,times(1)).findAll();
    }



}

