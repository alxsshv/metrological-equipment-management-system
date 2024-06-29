package main.service.implementations;



import jakarta.persistence.EntityNotFoundException;
import main.dto.rest.MiStandardDto;
import main.dto.rest.mappers.MiStandardDtoMapper;
import main.model.MeasurementInstrument;
import main.model.MiStandard;
import main.repository.MiStandardRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static testutils.TestEntityGenerator.*;

public class MiStandardServiceTest {
    private final MiStandardRepository miStandardRepository = Mockito.mock(MiStandardRepository.class);
    private final MiServiceImpl miService = Mockito.mock(MiServiceImpl.class);
    private final FileServiceImpl fileService = Mockito.mock(FileServiceImpl.class);
    private final MiStandardServiceImpl miStandardService = new MiStandardServiceImpl(miStandardRepository,miService,fileService);
    private final Pageable pageable = PageRequest.of(1,10, Sort.by(Sort.Direction.ASC, "arshinNumber"));
    private final MultipartFile[] files = {};
    private final String[] descriptions = {};

    @Test
    @DisplayName("Test save if parent measurement instrument is null")
    public void testSaveIfParentMeasurementInstrumentIsNull() throws IOException {
        long miStandardId = 1L;
        MiStandardDto miStandardDto = generateMiStandardDto(miStandardId);
        miStandardDto.setMeasurementInstrument(null);
        when(miService.getMiById(1L)).thenReturn(new MeasurementInstrument());
        when(miStandardRepository.findByArshinNumber(miStandardDto.getArshinNumber())).thenReturn(null);
        ResponseEntity<?> responseEntity =  miStandardService.save(miStandardDto, files, descriptions);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
    }

    @Test
    @DisplayName("Test save if miStandard already exist")
    public void testSaveIfMiStandardAlreadyExist() throws IOException {
        long miStandardId = 1L;
        MiStandardDto miStandardDto = generateMiStandardDto(miStandardId);
        when(miService.getMiById(1L)).thenReturn(new MeasurementInstrument());
        when(miStandardRepository.save(any(MiStandard.class))).thenReturn(new MiStandard());
        when(miStandardRepository.findByArshinNumber(miStandardDto.getArshinNumber())).thenReturn(new MiStandard());
        ResponseEntity<?> responseEntity =  miStandardService.save(miStandardDto, files, descriptions);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(miStandardRepository,never()).save(any(MiStandard.class));
    }

    @Test
    @DisplayName("Test save if arshinNumber is null")
    public void testSaveIfArshinNumberIsNull() throws IOException {
        long miStandardId = 1L;
        MiStandardDto miStandardDto = generateMiStandardDto(miStandardId);
        miStandardDto.setArshinNumber(null);
        when(miService.getMiById(1L)).thenReturn(new MeasurementInstrument());
        when(miStandardRepository.save(any(MiStandard.class))).thenReturn(new MiStandard());
        when(miStandardRepository.findByArshinNumber(miStandardDto.getArshinNumber())).thenReturn(new MiStandard());
        ResponseEntity<?> responseEntity =  miStandardService.save(miStandardDto, files, descriptions);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(miStandardRepository,never()).save(any(MiStandard.class));
    }

    @Test
    @DisplayName("testSaveIfNewMiStandardCreated")
    public void testSaveIfAllRequirementsAreMet() throws IOException {
        long miStandardId = 1L;
        MiStandardDto miStandardDto = generateMiStandardDto(miStandardId);
        when(miService.getMiById(1L)).thenReturn(new MeasurementInstrument());
        when(miStandardRepository.findByArshinNumber(miStandardDto.getArshinNumber())).thenReturn(null);
        when(miStandardRepository.save(any(MiStandard.class))).thenReturn(new MiStandard());
        ResponseEntity<?> responseEntity =  miStandardService.save(miStandardDto, files, descriptions);
        assertEquals("201 CREATED", responseEntity.getStatusCode().toString());
        verify(miStandardRepository,times(1)).save(any(MiStandard.class));
    }

    @Test
    @DisplayName("test FindById If Entity Found")
    public void testFindByIdIfEntityFound()  {
        long miStandardId = 1L;
        when(miStandardRepository.findById(miStandardId)).thenReturn(Optional.of(new MiStandard()));
        ResponseEntity<?> responseEntity =  miStandardService.findById(miStandardId);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
        verify(miStandardRepository,times(1)).findById(miStandardId);
    }

    @Test
    @DisplayName("test FindById If Entity Not Found")
    public void testFindByIdIfEntityNotFound() {
        long miStandardId = 1L;
        when(miStandardRepository.findById(miStandardId)).thenReturn(Optional.empty());
        ResponseEntity<?> responseEntity =  miStandardService.findById(miStandardId);
        assertEquals("404 NOT_FOUND", responseEntity.getStatusCode().toString());
        verify(miStandardRepository,times(1)).findById(miStandardId);
    }

    @Test
    @DisplayName("test getMiStandardId If Entity Found")
    public void testGetMiStandardByIdIfEntityFound() {
        long miStandardId = 1L;
        MiStandard expectedMiStandard = generateMiStandard(miStandardId);
        when(miStandardRepository.findById(miStandardId)).thenReturn(Optional.of(expectedMiStandard));
        MiStandard actualMiStandard =  miStandardService.getMiStandardById(miStandardId);
        assertEquals(expectedMiStandard, actualMiStandard);
        verify(miStandardRepository,times(1)).findById(miStandardId);
    }

    @Test
    @DisplayName("test getMiStandardId if entity not found")
    public void testGetMiStandardByIdIfEntityNotFound() throws EntityNotFoundException{
        long miStandardId = 1L;
        when(miStandardRepository.findById(miStandardId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class,
                ()-> miStandardService.getMiStandardById(miStandardId)) ;
        assertEquals("Запись об эталоне № 1 не найдена",exception.getMessage());
        verify(miStandardRepository,times(1)).findById(miStandardId);
    }

    @Test
    @DisplayName("Test findByArshinNumber if entity found")
    public void testFindByArshinNumberIfEntityFound() {
        MiStandard miStandard = generateMiStandard(1L);
        when(miStandardRepository.findByArshinNumber(miStandard.getArshinNumber()))
                .thenReturn(miStandard);
        ResponseEntity<?> responseEntity =  miStandardService.findByArshinNumber(miStandard.getArshinNumber());
        assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        verify(miStandardRepository,times(1)).findByArshinNumber(miStandard.getArshinNumber());
    }

    @Test
    @DisplayName("Test findByArshinNumber if entity not found")
    public void testFindByArshinNumberIfEntityNotFound() {
        MiStandard miStandard = generateMiStandard(1L);
        when(miStandardRepository.findByArshinNumber(miStandard.getArshinNumber()))
                .thenReturn(null);
        ResponseEntity<?> responseEntity =  miStandardService.findByArshinNumber(miStandard.getArshinNumber());
        assertEquals(HttpStatusCode.valueOf(404), responseEntity.getStatusCode());
        verify(miStandardRepository,times(1)).findByArshinNumber(miStandard.getArshinNumber());
    }

    @Test
    @DisplayName("Test findBySearchString if search string is empty")
    public void testFindBySearchStringIfSearchStringEmpty() {
        String saerchString = "";
        ResponseEntity<?> responseEntity = miStandardService.findBySearchString(saerchString);
        assertEquals(HttpStatusCode.valueOf(400), responseEntity.getStatusCode());
        verify(miStandardRepository,never()).findByArshinNumberContainingOrSchemaTitleContaining(saerchString, saerchString);
    }

    @Test
    @DisplayName("Test findBySearchString if search string is not empty")
    public void testFindBySearchStringIfSearchStringNotEmpty() {
        String saerchString = "abcd";
        when(miStandardRepository.findByArshinNumberContainingOrSchemaTitleContaining(saerchString, saerchString))
                .thenReturn(List.of(new MiStandard()));
        ResponseEntity<?> responseEntity = miStandardService.findBySearchString(saerchString);
        assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        verify(miStandardRepository,times(1))
                .findByArshinNumberContainingOrSchemaTitleContaining(saerchString, saerchString);
    }

    @Test
    @DisplayName("Test findBySearchString with pagination if search string is empty")
    public void testFindBySearchStringWithPaginationIfSearchStringEmpty() {
        String saerchString = "";
        ResponseEntity<?> responseEntity = miStandardService.findBySearchString(saerchString,pageable);
        assertEquals(HttpStatusCode.valueOf(400), responseEntity.getStatusCode());
        verify(miStandardRepository,never())
                .findByArshinNumberContainingOrSchemaTitleContaining(saerchString, saerchString,pageable);
    }

    @Test
    @DisplayName("Test findBySearchString with pagination if search string is not empty")
    public void testFindBySearchStringWithPaginationIfSearchStringNotEmpty() {
        String saerchString = "abcd";
        List<MiStandard> standards = List.of(new MiStandard(), new MiStandard());
        long totalPages = 1L;
        Page<MiStandard> page = new PageImpl<>(standards,pageable,totalPages);
        when(miStandardRepository
                .findByArshinNumberContainingOrSchemaTitleContaining(saerchString, saerchString,pageable))
                .thenReturn(page);
        ResponseEntity<?> responseEntity = miStandardService.findBySearchString(saerchString,pageable);
        assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        verify(miStandardRepository,times(1))
                .findByArshinNumberContainingOrSchemaTitleContaining(saerchString, saerchString,pageable);
    }

    @Test
    @DisplayName("Test findAll with pagination")
    public void testFindAllWithPagination() {
        long totalPages = 1L;
        List<MiStandard> standards = List.of(new MiStandard());
        Page<MiStandard> page = new PageImpl<>(standards,pageable,totalPages);
        when(miStandardRepository.findAll(pageable)).thenReturn(page);
        Page<MiStandardDto> page1 = miStandardService.findAll(pageable);
        assertEquals(page.getSize(), page1.getSize());
        verify(miStandardRepository,times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Test findAll")
    public void testFindAll() {
        List<MiStandard> standards = List.of(new MiStandard(), new MiStandard());
        when(miStandardRepository.findAll()).thenReturn(standards);
        List<MiStandardDto> dtos = miStandardService.findAll();
        assertEquals(standards.size(), dtos.size());
        verify(miStandardRepository,times(1)).findAll();
    }

    @Test
    @DisplayName("testUpdateOk")
    public void testUpdateOk()  {
        long miStandardId = 1L;
        MiStandardDto miStandardDto = generateMiStandardDto(miStandardId);
        MiStandard miStandard = MiStandardDtoMapper.mapToEntity(miStandardDto);
        when(miService.getMiById(1L)).thenReturn(new MeasurementInstrument());
        when(miStandardRepository.findById(miStandardId)).thenReturn(Optional.of(miStandard));
        ResponseEntity<?> responseEntity =  miStandardService.update(miStandardDto);
        assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        verify(miStandardRepository,times(1)).save(any(MiStandard.class));
    }

    @Test
    @DisplayName("test update if measurement instrument is null")
    public void testUpdateIfMeasurementInstrumentIsNull() {
        long miStandardId = 1L;
        MiStandardDto miStandardDto = generateMiStandardDto(miStandardId);
        miStandardDto.setMeasurementInstrument(null);
        MiStandard miStandard = MiStandardDtoMapper.mapToEntity(miStandardDto);
        when(miService.getMiById(1L)).thenReturn(new MeasurementInstrument());
        when(miStandardRepository.findById(miStandardId)).thenReturn(Optional.of(miStandard));
        ResponseEntity<?> responseEntity = miStandardService.update(miStandardDto);
        assertEquals(HttpStatusCode.valueOf(400), responseEntity.getStatusCode());
        verify(miStandardRepository,never()).save(any(MiStandard.class));
    }

    @Test
    @DisplayName("test update if miStandard not found")
    public void testUpdateIfMiStandardNotFound() {
        long miStandardId = 1L;
        MiStandardDto miStandardDto = generateMiStandardDto(miStandardId);
        when(miStandardRepository.findById(miStandardId)).thenReturn(Optional.empty());
        ResponseEntity<?> responseEntity = miStandardService.update(miStandardDto);
        assertEquals(HttpStatusCode.valueOf(400), responseEntity.getStatusCode());
        verify(miStandardRepository,never()).save(any(MiStandard.class));
    }

    @Test
    @DisplayName("Test delete")
    public void testDelete() {
        long id = 1L;
        ResponseEntity<?> responseEntity = miStandardService.delete(id);
        assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        verify(miStandardRepository,times(1)).deleteById(id);
    }



}
