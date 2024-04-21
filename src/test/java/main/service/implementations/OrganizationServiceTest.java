package main.service.implementations;

import main.dto.OrganizationDto;
import main.dto.mappers.OrganizationDtoMapper;
import main.model.Organization;
import main.repository.OrganizationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import testutils.TestDtoGenerator;
import testutils.TestEntityGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OrganizationServiceTest {
    private final OrganizationRepository organizationRepository = Mockito.mock(OrganizationRepository.class);
    private final OrganizationService organizationService = new OrganizationService(organizationRepository);
    private final Pageable pageable = PageRequest.of(0,10, Sort.by(Sort.Direction.ASC,"notation"));

    @Test
    @DisplayName("Test save if added new organisation")
    public void testSaveIfAddedNewOrganisation() {
        OrganizationDto organizationDto = TestDtoGenerator.generateOrganisationDtoWithId(1L);
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
        OrganizationDto organizationDto = TestDtoGenerator.generateOrganisationDtoWithId(1L);
        Organization organization = OrganizationDtoMapper.mapToEntity(organizationDto);
        when(organizationRepository.findByNotation(organizationDto.getNotation())).thenReturn(organization);
        ResponseEntity<?> responseEntity = organizationService.save(organizationDto);
        assertEquals("422 UNPROCESSABLE_ENTITY", responseEntity.getStatusCode().toString());
        verify(organizationRepository, times(1)).findByNotation(organizationDto.getNotation());
        verify(organizationRepository, never()).save(any(Organization.class));
    }

    @Test
    @DisplayName("Test save if organisation title is empty")
    public void testSaveIfOrganizationTitleIsEmpty() {
        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.setId(1L);
        organizationDto.setNotation("АО\"Организация\"");
        organizationDto.setAddress("г. Санкт-Петербург");
        Organization organization = OrganizationDtoMapper.mapToEntity(organizationDto);
        when(organizationRepository.findByNotation(organizationDto.getNotation())).thenReturn(organization);
        when(organizationRepository.save(organization)).thenReturn(organization);
        ResponseEntity<?> responseEntity = organizationService.save(organizationDto);
        assertEquals("422 UNPROCESSABLE_ENTITY", responseEntity.getStatusCode().toString());
        verify(organizationRepository, never()).findByNotation(organizationDto.getNotation());
        verify(organizationRepository, never()).save(any(Organization.class));
    }

    @Test
    @DisplayName("Test save if organisation notation is empty")
    public void testSaveIfOrganizationNotationIsEmpty() {
        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.setId(1L);
        organizationDto.setTitle("Акционерное общество \"Организация\"");
        organizationDto.setAddress("г. Санкт-Петербург");
        Organization organization = OrganizationDtoMapper.mapToEntity(organizationDto);
        when(organizationRepository.findByNotation(organizationDto.getNotation())).thenReturn(organization);
        when(organizationRepository.save(organization)).thenReturn(organization);
        ResponseEntity<?> responseEntity = organizationService.save(organizationDto);
        assertEquals("422 UNPROCESSABLE_ENTITY", responseEntity.getStatusCode().toString());
        verify(organizationRepository, never()).findByNotation(organizationDto.getNotation());
        verify(organizationRepository, never()).save(any(Organization.class));
    }

    @Test
    @DisplayName("Test update if organization is found")
    public void testUpdateIfOrganisationIsFound() {
        Long organizationId = 1L;
        OrganizationDto organizationDto = TestDtoGenerator.generateOrganisationDtoWithId(1L);
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
        OrganizationDto organizationDto = TestDtoGenerator.generateOrganisationDtoWithId(1L);
        when(organizationRepository.findById(organizationId)).thenReturn(Optional.empty());
        ResponseEntity<?> responseEntity = organizationService.update(organizationDto);
        assertEquals("404 NOT_FOUND", responseEntity.getStatusCode().toString());
        verify(organizationRepository, times(1)).findById(organizationId);
        verify(organizationRepository, never()).save(any(Organization.class));
    }

    @Test
    @DisplayName("Test update if organization title is empty")
    public void testUpdateIfOrganizationTitleIsEmpty() {
        Long organizationId = 1L;
        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.setId(organizationId);
        organizationDto.setNotation("АО\"Организация\"");
        organizationDto.setAddress("г. Екатеринбург");
        Organization organization = OrganizationDtoMapper.mapToEntity(organizationDto);
        when(organizationRepository.findById(organizationId)).thenReturn(Optional.of(organization));
        ResponseEntity<?> responseEntity = organizationService.update(organizationDto);
        assertEquals("422 UNPROCESSABLE_ENTITY", responseEntity.getStatusCode().toString());
        verify(organizationRepository, never()).findById(organizationId);
        verify(organizationRepository, never()).save(organization);
    }

    @Test
    @DisplayName("Test update if organization notation is empty")
    public void testUpdateIfOrganizationNotationIsEmpty() {
        Long organizationId = 1L;
        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.setId(organizationId);
        organizationDto.setTitle("Акционерное общество \"Организация\"");
        organizationDto.setAddress("г. Екатеринбург");
        Organization organization = OrganizationDtoMapper.mapToEntity(organizationDto);
        when(organizationRepository.findById(organizationId)).thenReturn(Optional.of(organization));
        ResponseEntity<?> responseEntity = organizationService.update(organizationDto);
        assertEquals("422 UNPROCESSABLE_ENTITY", responseEntity.getStatusCode().toString());
        verify(organizationRepository, never()).findById(organizationId);
        verify(organizationRepository, never()).save(organization);
    }

    @Test
    @DisplayName("Test delete if organization is found")
    public void testDeleteIfOrganisationIsFound(){
        Long organizationId = 1L;
        OrganizationDto organizationDto = TestDtoGenerator.generateOrganisationDtoWithId(organizationId);
        Organization organization = OrganizationDtoMapper.mapToEntity(organizationDto);
        when(organizationRepository.findById(organizationId)).thenReturn(Optional.of(organization));
        ResponseEntity<?> responseEntity = organizationService.delete(organizationId);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
        verify(organizationRepository, times(1)).findById(organizationId);
        verify(organizationRepository, times(1)).delete(any(Organization.class));
    }

    @Test
    @DisplayName("Test delete if organization not found")
    public void testDeleteIfOrganisationNotFound(){
        long organisationId = 1L;
        when(organizationRepository.findById(organisationId)).thenReturn(Optional.empty());
        ResponseEntity<?> responseEntity = organizationService.delete(organisationId);
        assertEquals("404 NOT_FOUND", responseEntity.getStatusCode().toString());
        verify(organizationRepository, times(1)).findById(organisationId);
        verify(organizationRepository, never()).delete(any(Organization.class));
    }

    @Test
    @DisplayName("Test findById if organization is found")
    public void testFindByIdIfOrganizationIsFound(){
        long organizationId = 1L;
        Organization organization = TestEntityGenerator.generateOrganizationWithId(organizationId);
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
        ResponseEntity<?> responseEntity = organizationService.findBySearchStringWithPages(searchString, pageable);
        assertEquals("400 BAD_REQUEST",responseEntity.getStatusCode().toString());
        verify(organizationRepository,never()).findByTitleContainingOrNotationContaining(searchString,searchString,pageable);
    }

    @Test
    @DisplayName("Test findBySearchStringWithPages if search string is not empty")
    public void testFindBySearchStringWithPagesIfSearchStringIsNotEmpty(){
        String searchString = "АО\"Организация\"";
        Page<Organization> organizationsPage = Page.empty();
        when(organizationRepository.findByTitleContainingOrNotationContaining(searchString, searchString, pageable))
                .thenReturn(organizationsPage);
        ResponseEntity<?> responseEntity = organizationService.findBySearchStringWithPages(searchString,pageable);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
        verify(organizationRepository,times(1))
                .findByTitleContainingOrNotationContaining(searchString,searchString, pageable);
    }

    @Test
    @DisplayName("Test findAll with pages")
    public void testFindAllWithPages(){
        int totalPages = 1;
        Organization organization = TestEntityGenerator.generateOrganizationWithId(1L);
        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);
        Page<Organization> organizationPage = new PageImpl<>(organizations,pageable,totalPages);
        when(organizationRepository.findAll(pageable)).thenReturn(organizationPage);
        Page<OrganizationDto> organizationDtos = organizationService.findAll(pageable);
        assertEquals(organizationPage.getContent().size(), organizationDtos.getContent().size());
        verify(organizationRepository,times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Test findAll without pages")
    public void testFindAllWithoutPages(){
        Organization organization = TestEntityGenerator.generateOrganizationWithId(1L);
        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization);
        when(organizationRepository.findAll()).thenReturn(organizations);
        List<OrganizationDto> organizationDtos = organizationService.findAll();
        assertEquals(organizations.size(), organizationDtos.size());
        verify(organizationRepository,times(1)).findAll();
    }



}

