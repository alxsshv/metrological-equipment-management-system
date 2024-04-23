package main.service.implementations;

import main.dto.MiDto;
import main.dto.MiFullDto;
import main.dto.mappers.MeasurementInstrumentMapper;
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
import testutils.TestDtoGenerator;
import testutils.TestEntityGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MeasurementInstrumentServiceTest {
    private final MeasurementInstrumentRepository miRepository = Mockito.mock(MeasurementInstrumentRepository.class);
    private final OrganizationRepository organizationRepository = Mockito.mock(OrganizationRepository.class);
    private final MiTypeRepository miTypeRepository = Mockito.mock(MiTypeRepository.class);
    private final FileService fileService = Mockito.mock(FileService.class);
    private final Pageable pageable = PageRequest.of(1, 10,
            Sort.by(Sort.Direction.ASC, "modification","serialNum"));

    public MeasurementInstrumentService measurementInstrumentService =
            new MeasurementInstrumentService(miRepository, organizationRepository, miTypeRepository, fileService);


    @Test
    @DisplayName("Test save if created new measurement instrument")
    public void testSaveIfCreatedNewMeasurementInstrument() throws IOException {
        long miId = 1L;
        MiFullDto miFullDto = TestDtoGenerator.generateMeasurementInstrumentFullDto(miId);
        when(miTypeRepository.findById(anyLong())).thenReturn(Optional.of(miFullDto.getMiType()));
        when(organizationRepository.findById(anyLong())).thenReturn(Optional.of(miFullDto.getOwner()));
        when(miRepository.findByModificationAndSerialNum(miFullDto.getModification(), miFullDto.getSerialNum()))
                .thenReturn(null);
        MeasurementInstrument mi = MeasurementInstrumentMapper.mapToEntity(miFullDto);
        when(miRepository.save(any(MeasurementInstrument.class))).thenReturn(mi);
        MultipartFile[] files = {};
        String[] descriptions = {};
        ResponseEntity<?> responseEntity = measurementInstrumentService.save(miFullDto,files,descriptions);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
        verify(miRepository, times(1))
                .findByModificationAndSerialNum(miFullDto.getModification(), miFullDto.getSerialNum());
        verify(miRepository, times(1)).save(any(MeasurementInstrument.class));
    }

    @Test
    @DisplayName("Test save if measurement instrument already exist")
    public void testSaveIfMeasurementInstrumentAlreadyExist() throws IOException {
        long miId = 1L;
        MiFullDto miFullDto = TestDtoGenerator.generateMeasurementInstrumentFullDto(miId);
        when(miTypeRepository.findById(anyLong())).thenReturn(Optional.of(miFullDto.getMiType()));
        when(organizationRepository.findById(anyLong())).thenReturn(Optional.of(miFullDto.getOwner()));
        MeasurementInstrument mi = MeasurementInstrumentMapper.mapToEntity(miFullDto);
        when(miRepository.findByModificationAndSerialNum(miFullDto.getModification(), miFullDto.getSerialNum()))
                .thenReturn(mi);
        MultipartFile[] files = {};
        String[] descriptions = {};
        ResponseEntity<?> responseEntity = measurementInstrumentService.save(miFullDto,files,descriptions);
        assertEquals("422 UNPROCESSABLE_ENTITY", responseEntity.getStatusCode().toString());
        verify(miRepository, never()).save(any(MeasurementInstrument.class));
    }

    @Test
    @DisplayName("Test save if modification is empty")
    public void testSaveIfModificationIsEmpty() throws IOException {
        long miId = 1L;
        MiFullDto miFullDto = new MiFullDto();
        miFullDto.setId(miId);
        miFullDto.setSerialNum("SN1");
        miFullDto.setOwner(TestEntityGenerator.generateOrganizationWithId(1L));
        miFullDto.setMiType(TestEntityGenerator.generateMiTypeWithId(1L));
        miFullDto.setUser("User");
        MultipartFile[] files = {};
        String[] descriptions = {};
        ResponseEntity<?> responseEntity = measurementInstrumentService.save(miFullDto,files,descriptions);
        assertEquals("422 UNPROCESSABLE_ENTITY", responseEntity.getStatusCode().toString());
        verify(miRepository, never()).save(any(MeasurementInstrument.class));
    }

    @Test
    @DisplayName("Test save if serial number is empty")
    public void testSaveIfSerialNumberIsEmpty() throws IOException {
        long miId = 1L;
        MiFullDto miFullDto = new MiFullDto();
        miFullDto.setId(miId);
        miFullDto.setModification("В7-78/1");
        miFullDto.setOwner(TestEntityGenerator.generateOrganizationWithId(1L));
        miFullDto.setMiType(TestEntityGenerator.generateMiTypeWithId(1L));
        miFullDto.setUser("User");
        MultipartFile[] files = {};
        String[] descriptions = {};
        ResponseEntity<?> responseEntity = measurementInstrumentService.save(miFullDto,files,descriptions);
        assertEquals("422 UNPROCESSABLE_ENTITY", responseEntity.getStatusCode().toString());
        verify(miRepository, never()).save(any(MeasurementInstrument.class));
    }

    @Test
    @DisplayName("Test save if miType is empty")
    public void testSaveIfMiTypeIsEmpty() throws IOException {
        long miId = 1L;
        MiFullDto miFullDto = new MiFullDto();
        miFullDto.setId(miId);
        miFullDto.setModification("В7-78/1");
        miFullDto.setSerialNum("SN1");
        miFullDto.setOwner(TestEntityGenerator.generateOrganizationWithId(1L));
        miFullDto.setUser("User");
        MultipartFile[] files = {};
        String[] descriptions = {};
        ResponseEntity<?> responseEntity = measurementInstrumentService.save(miFullDto,files,descriptions);
        assertEquals("422 UNPROCESSABLE_ENTITY", responseEntity.getStatusCode().toString());
        verify(miRepository, never()).save(any(MeasurementInstrument.class));
    }

    @Test
    @DisplayName("Test save if owner is empty")
    public void testSaveIfOwnerIsEmpty() throws IOException {
        long miId = 1L;
        MiFullDto miFullDto = new MiFullDto();
        miFullDto.setId(miId);
        miFullDto.setModification("В7-78/1");
        miFullDto.setSerialNum("SN1");
        miFullDto.setMiType(TestEntityGenerator.generateMiTypeWithId(1L));
        miFullDto.setUser("User");
        MultipartFile[] files = {};
        String[] descriptions = {};
        ResponseEntity<?> responseEntity = measurementInstrumentService.save(miFullDto,files,descriptions);
        assertEquals("422 UNPROCESSABLE_ENTITY", responseEntity.getStatusCode().toString());
        verify(miRepository, never()).save(any(MeasurementInstrument.class));
    }

    @Test
    @DisplayName("Test update if updated measurement instrument not found")
    public void testUpdateIfUpdatedMeasurementInstrumentNotFound(){
        long miId = 1L;
        MiFullDto miFullDto = TestDtoGenerator.generateMeasurementInstrumentFullDto(miId);
        when(miTypeRepository.findById(anyLong())).thenReturn(Optional.of(miFullDto.getMiType()));
        when(organizationRepository.findById(anyLong())).thenReturn(Optional.of(miFullDto.getOwner()));
        when(miRepository.findById(miId)).thenReturn(Optional.empty());
        ResponseEntity<?> responseEntity = measurementInstrumentService.update(miFullDto);
        assertEquals("404 NOT_FOUND", responseEntity.getStatusCode().toString());
        verify(miRepository, times(1)).findById(miId);
        verify(miRepository, never()).save(any(MeasurementInstrument.class));
    }

    @Test
    @DisplayName("Test update if updated measurement instrument found")
    public void testUpdateIfUpdatedMeasurementInstrumentFound(){
        long miId = 1L;
        MiFullDto miFullDto = TestDtoGenerator.generateMeasurementInstrumentFullDto(miId);
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
        long miId = 1L;
        MiFullDto miFullDto = new MiFullDto();
        miFullDto.setId(miId);
        miFullDto.setSerialNum("SN1");
        miFullDto.setOwner(TestEntityGenerator.generateOrganizationWithId(1L));
        miFullDto.setMiType(TestEntityGenerator.generateMiTypeWithId(1L));
        miFullDto.setUser("User");
        ResponseEntity<?> responseEntity = measurementInstrumentService.update(miFullDto);
        assertEquals("422 UNPROCESSABLE_ENTITY", responseEntity.getStatusCode().toString());
        verify(miRepository, never()).save(any(MeasurementInstrument.class));
    }

    @Test
    @DisplayName("Test update if serial number is empty")
    public void testUpdateIfSerialNumberIsEmpty(){
        long miId = 1L;
        MiFullDto miFullDto = new MiFullDto();
        miFullDto.setId(miId);
        miFullDto.setModification("В7-78/1");
        miFullDto.setOwner(TestEntityGenerator.generateOrganizationWithId(1L));
        miFullDto.setMiType(TestEntityGenerator.generateMiTypeWithId(1L));
        miFullDto.setUser("User");
        ResponseEntity<?> responseEntity = measurementInstrumentService.update(miFullDto);
        assertEquals("422 UNPROCESSABLE_ENTITY", responseEntity.getStatusCode().toString());
        verify(miRepository, never()).save(any(MeasurementInstrument.class));
    }

    @Test
    @DisplayName("Test update if miType is empty")
    public void testUpdateIfMiTypeIsEmpty(){
        long miId = 1L;
        MiFullDto miFullDto = new MiFullDto();
        miFullDto.setId(miId);
        miFullDto.setModification("В7-78/1");
        miFullDto.setSerialNum("SN1");
        miFullDto.setOwner(TestEntityGenerator.generateOrganizationWithId(1L));
        miFullDto.setUser("User");
        ResponseEntity<?> responseEntity = measurementInstrumentService.update(miFullDto);
        assertEquals("422 UNPROCESSABLE_ENTITY", responseEntity.getStatusCode().toString());
        verify(miRepository, never()).save(any(MeasurementInstrument.class));
    }

    @Test
    @DisplayName("Test update if owner is empty")
    public void testUpdateIfOwnerIsEmpty(){
        long miId = 1L;
        MiFullDto miFullDto = new MiFullDto();
        miFullDto.setId(miId);
        miFullDto.setModification("В7-78/1");
        miFullDto.setSerialNum("SN1");
        miFullDto.setMiType(TestEntityGenerator.generateMiTypeWithId(1L));
        miFullDto.setUser("User");
        ResponseEntity<?> responseEntity = measurementInstrumentService.update(miFullDto);
        assertEquals("422 UNPROCESSABLE_ENTITY", responseEntity.getStatusCode().toString());
        verify(miRepository, never()).save(any(MeasurementInstrument.class));
    }




    @Test
    @DisplayName("Test delete if measurement instrument not found")
    public void testDeleteIfMeasurementInstrumentNotFound(){
        long miId = 1L;
        when(miRepository.findById(miId)).thenReturn(Optional.empty());
        ResponseEntity<?> responseEntity = measurementInstrumentService.delete(miId);
        assertEquals("404 NOT_FOUND", responseEntity.getStatusCode().toString());
        verify(miRepository, never()).delete(any(MeasurementInstrument.class));
    }

    @Test
    @DisplayName("Test delete if measurement instrument is found")
    public void testDeleteIfMeasurementInstrumentIsFound(){
        long miId = 1L;
        MeasurementInstrument mi = TestEntityGenerator.generateMeasurementInstrumentWithId(miId);
        when(miRepository.findById(miId)).thenReturn(Optional.of(mi));
        ResponseEntity<?> responseEntity = measurementInstrumentService.delete(miId);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
        verify(miRepository, times(1)).delete(mi);
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
        MeasurementInstrument mi = TestEntityGenerator.generateMeasurementInstrumentWithId(miId);
        when(miRepository.findById(miId)).thenReturn(Optional.of(mi));
        ResponseEntity<?> responseEntity = measurementInstrumentService.findById(miId);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
    }

    @Test
    @DisplayName("Test findBySearchString if searchString is empty")
    public void testFindBySearchStringIfSearchStringIsEmpty(){
        String searchString = "";
        ResponseEntity<?> responseEntity = measurementInstrumentService.findBySearchString(searchString, pageable);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(miRepository, never())
                .findByModificationContainingOrSerialNumContainingOrInventoryNumContaining(searchString,
                        searchString,searchString,pageable);
    }

    @Test
    @DisplayName("Test findBySearchString if searchString is not empty")
    public void testFindBySearchStringIfSearchStringIsNotEmpty(){
        String searchString = "В7-78";
        MeasurementInstrument mi = TestEntityGenerator.generateMeasurementInstrumentWithId(1L);
        List<MeasurementInstrument> mis = new ArrayList<>();
        mis.add(mi);
        long totalPages = 1L;
        Page<MeasurementInstrument> page = new PageImpl<>(mis,pageable,totalPages);
        when(miRepository
                .findByModificationContainingOrSerialNumContainingOrInventoryNumContaining(searchString,
                        searchString,searchString,pageable)).thenReturn(page);
        ResponseEntity<?> responseEntity = measurementInstrumentService.findBySearchString(searchString, pageable);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
        verify(miRepository, times(1))
                .findByModificationContainingOrSerialNumContainingOrInventoryNumContaining(searchString,
                        searchString,searchString,pageable);
    }

    @Test
    @DisplayName("Test findAll without pages")
    public void testFindAllWithoutPages() {
        MeasurementInstrument mi = TestEntityGenerator.generateMeasurementInstrumentWithId(1L);
        List<MeasurementInstrument> mis = new ArrayList<>();
        mis.add(mi);
        when(miRepository.findAll()).thenReturn(mis);
        List<MiDto> misDto = measurementInstrumentService.findAll();
        assertEquals(mis.size(),misDto.size());
        verify(miRepository,times(1)).findAll();
    }

    @Test
    @DisplayName("Test findAll with pages")
    public void testFindAllWithPages() {
        long totalPages = 1L;
        MeasurementInstrument mi = TestEntityGenerator.generateMeasurementInstrumentWithId(1L);
        List<MeasurementInstrument> mis = new ArrayList<>();
        mis.add(mi);
        Page<MeasurementInstrument> page = new PageImpl<>(mis,pageable,totalPages);
        when(miRepository.findAll(pageable)).thenReturn(page);
        Page<MiDto> miPage = measurementInstrumentService.findAll(pageable);
        assertEquals(mis.size(),miPage.getContent().size());
        verify(miRepository,times(1)).findAll(pageable);
    }



}