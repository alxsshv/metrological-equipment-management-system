package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import main.dto.rest.MiDto;
import main.dto.rest.MiFullDto;
import main.dto.rest.mappers.MeasurementInstrumentMapper;
import main.model.MeasurementInstrument;
import main.repository.MeasurementInstrumentRepository;
import main.repository.MiTypeRepository;
import main.repository.OrganizationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static testutils.TestEntityGenerator.*;

public class MeasurementInstrumentServiceTest {
    private final MeasurementInstrumentRepository miRepository = Mockito.mock(MeasurementInstrumentRepository.class);
    private final OrganizationRepository organizationRepository = Mockito.mock(OrganizationRepository.class);
    private final MiTypeRepository miTypeRepository = Mockito.mock(MiTypeRepository.class);
    private final FileServiceImpl fileService = Mockito.mock(FileServiceImpl.class);
    private final Pageable pageable = PageRequest.of(1, 10,
            Sort.by(Sort.Direction.ASC, "modification","serialNum"));
    private final MultipartFile[] files = {};
    private final String[] descriptions = {};

    public MiServiceImpl measurementInstrumentService =
            new MiServiceImpl(miRepository, organizationRepository, miTypeRepository, fileService);


    @Test
    @DisplayName("Test save if created new measurement instrument")
    public void testSaveIfCreatedNewMeasurementInstrument() throws IOException {
        MiFullDto miFullDto = generateMeasurementInstrumentFullDto(1L);
        when(miTypeRepository.findById(anyLong())).thenReturn(Optional.of(miFullDto.getMiType()));
        when(organizationRepository.findById(anyLong())).thenReturn(Optional.of(miFullDto.getOwner()));
        when(miRepository.findByModificationAndSerialNum(miFullDto.getModification(), miFullDto.getSerialNum()))
                .thenReturn(null);
        MeasurementInstrument mi = MeasurementInstrumentMapper.mapToEntity(miFullDto);
        when(miRepository.save(any(MeasurementInstrument.class))).thenReturn(mi);
        ResponseEntity<?> responseEntity = measurementInstrumentService.save(miFullDto,files,descriptions);
        assertEquals("201 CREATED", responseEntity.getStatusCode().toString());
        verify(miRepository, times(1))
                .findByModificationAndSerialNum(miFullDto.getModification(), miFullDto.getSerialNum());
        verify(miRepository, times(1)).save(any(MeasurementInstrument.class));
    }

    @Test
    @DisplayName("Test save if measurement instrument already exist")
    public void testSaveIfMeasurementInstrumentAlreadyExist() throws IOException {
        MiFullDto miFullDto = generateMeasurementInstrumentFullDto(1L);
        when(miTypeRepository.findById(anyLong())).thenReturn(Optional.of(miFullDto.getMiType()));
        when(organizationRepository.findById(anyLong())).thenReturn(Optional.of(miFullDto.getOwner()));
        MeasurementInstrument mi = MeasurementInstrumentMapper.mapToEntity(miFullDto);
        when(miRepository.findByModificationAndSerialNum(miFullDto.getModification(), miFullDto.getSerialNum()))
                .thenReturn(mi);
        ResponseEntity<?> responseEntity = measurementInstrumentService.save(miFullDto,files,descriptions);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(miRepository, never()).save(any(MeasurementInstrument.class));
    }

    @Test
    @DisplayName("Test save if modification is empty")
    public void testSaveIfModificationIsEmpty() throws IOException {
        MiFullDto miFullDto = generateMeasurementInstrumentFullDto(1L);
        miFullDto.setModification(null);
        ResponseEntity<?> responseEntity = measurementInstrumentService.save(miFullDto,files,descriptions);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(miRepository, never()).save(any(MeasurementInstrument.class));
    }

    @Test
    @DisplayName("Test save if serial number is empty")
    public void testSaveIfSerialNumberIsEmpty() throws IOException {
        long miId = 1L;
        MiFullDto miFullDto = generateMeasurementInstrumentFullDto(miId);
        miFullDto.setSerialNum("");
        ResponseEntity<?> responseEntity = measurementInstrumentService.save(miFullDto,files,descriptions);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(miRepository, never()).save(any(MeasurementInstrument.class));
    }

    @Test
    @DisplayName("Test save if miType is empty")
    public void testSaveIfMiTypeIsEmpty() throws IOException {
        MiFullDto miFullDto = generateMeasurementInstrumentFullDto(1L);
        miFullDto.setMiType(null);
        ResponseEntity<?> responseEntity = measurementInstrumentService.save(miFullDto,files,descriptions);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(miRepository, never()).save(any(MeasurementInstrument.class));
    }

    @Test
    @DisplayName("Test save if owner is empty")
    public void testSaveIfOwnerIsEmpty() throws IOException {
        MiFullDto miFullDto = generateMeasurementInstrumentFullDto(1L);
        miFullDto.setOwner(null);
        ResponseEntity<?> responseEntity = measurementInstrumentService.save(miFullDto,files,descriptions);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(miRepository, never()).save(any(MeasurementInstrument.class));
    }

    @Test
    @DisplayName("Test update if updated measurement instrument not found")
    public void testUpdateIfUpdatedMeasurementInstrumentNotFound(){
        long miId = 1L;
        MiFullDto miFullDto = generateMeasurementInstrumentFullDto(miId);
        when(miTypeRepository.findById(anyLong())).thenReturn(Optional.of(miFullDto.getMiType()));
        when(organizationRepository.findById(anyLong())).thenReturn(Optional.of(miFullDto.getOwner()));
        when(miRepository.findById(miId)).thenReturn(Optional.empty());
        ResponseEntity<?> responseEntity = measurementInstrumentService.update(miFullDto);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(miRepository, times(1)).findById(miId);
        verify(miRepository, never()).save(any(MeasurementInstrument.class));
    }

    @Test
    @DisplayName("Test update if updated measurement instrument found")
    public void testUpdateIfUpdatedMeasurementInstrumentFound(){
        long miId = 1L;
        MiFullDto miFullDto = generateMeasurementInstrumentFullDto(miId);
        when(miTypeRepository.findById(anyLong())).thenReturn(Optional.of(miFullDto.getMiType()));
        when(organizationRepository.findById(anyLong())).thenReturn(Optional.of(miFullDto.getOwner()));
        MeasurementInstrument measurementInstrument = MeasurementInstrumentMapper.mapToEntity(miFullDto);
        when(miRepository.findById(miId)).thenReturn(Optional.of(measurementInstrument));
        ResponseEntity<?> responseEntity = measurementInstrumentService.update(miFullDto);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
        verify(miRepository, times(1)).findById(miId);
        verify(miRepository, times(1)).save(measurementInstrument);
    }

    @Test
    @DisplayName("Test update if modification is empty")
    public void testUpdateIfModificationIsEmpty(){
        MiFullDto miFullDto = generateMeasurementInstrumentFullDto(1L);
        miFullDto.setModification(null);
        ResponseEntity<?> responseEntity = measurementInstrumentService.update(miFullDto);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(miRepository, never()).save(any(MeasurementInstrument.class));
    }

    @Test
    @DisplayName("Test update if serial number is empty")
    public void testUpdateIfSerialNumberIsEmpty(){
        MiFullDto miFullDto = generateMeasurementInstrumentFullDto(1L);
        miFullDto.setSerialNum(null);
        ResponseEntity<?> responseEntity = measurementInstrumentService.update(miFullDto);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(miRepository, never()).save(any(MeasurementInstrument.class));
    }

    @Test
    @DisplayName("Test update if miType is empty")
    public void testUpdateIfMiTypeIsEmpty(){
        MiFullDto miFullDto = generateMeasurementInstrumentFullDto(1L);
        miFullDto.setMiType(null);
        ResponseEntity<?> responseEntity = measurementInstrumentService.update(miFullDto);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(miRepository, never()).save(any(MeasurementInstrument.class));
    }

    @Test
    @DisplayName("Test update if owner is empty")
    public void testUpdateIfOwnerIsEmpty(){
        MiFullDto miFullDto =generateMeasurementInstrumentFullDto(1L);
        miFullDto.setOwner(null);
        ResponseEntity<?> responseEntity = measurementInstrumentService.update(miFullDto);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(miRepository, never()).save(any(MeasurementInstrument.class));
    }


    @Test
    @DisplayName("Test delete if Mi found")
    public void testDeleteIfMeasurementInstrumentIsFound(){
        long miId = 1L;
        MeasurementInstrument mi = generateMeasurementInstrument(miId);
        when(miRepository.findById(miId)).thenReturn(Optional.of(mi));
        ResponseEntity<?> responseEntity = measurementInstrumentService.delete(miId);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
        verify(miRepository, times(1)).deleteById(miId);
    }

    @Test
    @DisplayName("Test delete if Mi not found")
    public void testDeleteIfMeasurementInstrumentIsNotFound(){
        long miId = 1L;
        when(miRepository.findById(miId)).thenReturn(Optional.empty());
        ResponseEntity<?> responseEntity = measurementInstrumentService.delete(miId);
        assertEquals("404 NOT_FOUND", responseEntity.getStatusCode().toString());
        verify(miRepository, never()).deleteById(miId);
    }


    @Test
    @DisplayName("Test findById if measurement instrument not found")
    public void testFindByIdIfMeasurementInstrumentNotFound(){
        long miId = 1L;
        when(miRepository.findById(miId)).thenReturn(Optional.empty());
        ResponseEntity<?> responseEntity = measurementInstrumentService.findById(miId);
        assertEquals("404 NOT_FOUND", responseEntity.getStatusCode().toString());
    }

    @Test
    @DisplayName("Test findById if measurement instrument is found")
    public void testFindByIdIfMeasurementInstrumentIsFound(){
        long miId = 1L;
        MeasurementInstrument mi = generateMeasurementInstrument(miId);
        when(miRepository.findById(miId)).thenReturn(Optional.of(mi));
        ResponseEntity<?> responseEntity = measurementInstrumentService.findById(miId);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
    }

    @Test
    @DisplayName("test getMiById if entity not found")
    public void testGetMiByIdIfEntityNotFound() throws EntityNotFoundException {
        long miStandardId = 1L;
        when(miRepository.findById(miStandardId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class,
                ()-> measurementInstrumentService.getMiById(miStandardId)) ;
        assertEquals("Средство измерений № 1 не найдено",exception.getMessage());
        verify(miRepository,times(1)).findById(miStandardId);
    }

    @Test
    @DisplayName("test getMiById if entity found")
    public void testGetMiByIdIfEntityFound() throws EntityNotFoundException {
        long miStandardId = 1L;
        MeasurementInstrument mi = generateMeasurementInstrument(miStandardId);
        when(miRepository.findById(miStandardId)).thenReturn(Optional.of(mi));
        MeasurementInstrument measurementInstrument = measurementInstrumentService.getMiById(miStandardId);
        assertEquals(measurementInstrument, mi);
        verify(miRepository,times(1)).findById(miStandardId);
    }


    @Test
    @DisplayName("Test findBySearchString if searchString is empty")
    public void testFindBySearchStringIfSearchStringIsEmpty(){
        String searchString = "";
        ResponseEntity<?> responseEntity = measurementInstrumentService.findBySearchString(searchString, pageable);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(miRepository, never())
                .findByModificationIgnoreCaseContainingOrSerialNumIgnoreCaseContainingOrInventoryNumIgnoreCaseContaining(searchString,
                        searchString,searchString,pageable);
    }

    @Test
    @DisplayName("Test findBySearchString if searchString is not empty")
    public void testFindBySearchStringIfSearchStringIsNotEmpty(){
        String searchString = "В7-78";
        MeasurementInstrument mi = generateMeasurementInstrument(1L);
        List<MeasurementInstrument> mis = List.of(mi);
        long totalPages = 1L;
        Page<MeasurementInstrument> page = new PageImpl<>(mis,pageable,totalPages);
        when(miRepository
                .findByModificationIgnoreCaseContainingOrSerialNumIgnoreCaseContainingOrInventoryNumIgnoreCaseContaining(searchString,
                        searchString,searchString,pageable)).thenReturn(page);
        ResponseEntity<?> responseEntity = measurementInstrumentService.findBySearchString(searchString, pageable);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
        verify(miRepository, times(1))
                .findByModificationIgnoreCaseContainingOrSerialNumIgnoreCaseContainingOrInventoryNumIgnoreCaseContaining(searchString,
                        searchString,searchString,pageable);
    }

    @Test
    @DisplayName("Test findAll without pages")
    public void testFindAllWithoutPages() {
        MeasurementInstrument mi = generateMeasurementInstrument(1L);
        List<MeasurementInstrument> mis = List.of(mi);
        when(miRepository.findAll()).thenReturn(mis);
        List<MiDto> misDto = measurementInstrumentService.findAll();
        assertEquals(mis.size(),misDto.size());
        verify(miRepository,times(1)).findAll();
    }

    @Test
    @DisplayName("Test findAll with pages")
    public void testFindAllWithPages() {
        long totalPages = 1L;
        MeasurementInstrument mi = generateMeasurementInstrument(1L);
        List<MeasurementInstrument> mis = List.of(mi);
        Page<MeasurementInstrument> page = new PageImpl<>(mis,pageable,totalPages);
        when(miRepository.findAll(pageable)).thenReturn(page);
        Page<MiDto> miPage = measurementInstrumentService.findAll(pageable);
        assertEquals(mis.size(),miPage.getContent().size());
        verify(miRepository,times(1)).findAll(pageable);
    }
}