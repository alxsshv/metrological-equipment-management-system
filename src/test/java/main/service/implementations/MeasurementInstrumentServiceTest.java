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
import testutils.TestDtoGenerator;
import testutils.TestEntityGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MeasurementInstrumentServiceTest {
    private final MeasurementInstrumentRepository miRepository = Mockito.mock(MeasurementInstrumentRepository.class);
    private final OrganizationRepository organizationRepository = Mockito.mock(OrganizationRepository.class);
    private final MiTypeRepository miTypeRepository = Mockito.mock(MiTypeRepository.class);
    private final Pageable pageable = PageRequest.of(1, 10,
            Sort.by(Sort.Direction.ASC, "modification","serialNum"));

    public MeasurementInstrumentService measurementInstrumentService =
            new MeasurementInstrumentService(miRepository, organizationRepository, miTypeRepository);


    @Test
    @DisplayName("Test save if created new measurement instrument")
    public void testSaveIfCreatedNewMeasurementInstrument(){
        long miId = 1L;
        MiFullDto miFullDto = TestDtoGenerator.generateMeasurementInstrumentFullDto(miId);
        when(miTypeRepository.findById(anyLong())).thenReturn(Optional.of(miFullDto.getMiType()));
        when(organizationRepository.findById(anyLong())).thenReturn(Optional.of(miFullDto.getOwner()));
        when(miRepository.findByModificationAndSerialNum(miFullDto.getModification(), miFullDto.getSerialNum()))
                .thenReturn(null);
        ResponseEntity<?> responseEntity = measurementInstrumentService.save(miFullDto);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
        verify(miRepository, times(1))
                .findByModificationAndSerialNum(miFullDto.getModification(), miFullDto.getSerialNum());
        verify(miRepository, times(1)).save(any(MeasurementInstrument.class));
    }

    @Test
    @DisplayName("Test save if measurement instrument already exist")
    public void testSaveIfMeasurementInstrumentAlreadyExist(){
        long miId = 1L;
        MiFullDto miFullDto = TestDtoGenerator.generateMeasurementInstrumentFullDto(miId);
        when(miTypeRepository.findById(anyLong())).thenReturn(Optional.of(miFullDto.getMiType()));
        when(organizationRepository.findById(anyLong())).thenReturn(Optional.of(miFullDto.getOwner()));
        MeasurementInstrument mi = MeasurementInstrumentMapper.mapToEntity(miFullDto);
        when(miRepository.findByModificationAndSerialNum(miFullDto.getModification(), miFullDto.getSerialNum()))
                .thenReturn(mi);
        ResponseEntity<?> responseEntity = measurementInstrumentService.save(miFullDto);
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