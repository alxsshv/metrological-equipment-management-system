package main.service.implementations;


import jakarta.persistence.EntityNotFoundException;
import main.dto.rest.MiDto;
import main.dto.rest.MiFullDto;
import main.model.MeasurementInstrument;
import main.model.Organization;
import main.repository.MeasurementInstrumentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class MeasurementInstrumentServiceTest {
    private final MeasurementInstrumentRepository miRepository = Mockito.mock(MeasurementInstrumentRepository.class);
    private final Pageable pageable = PageRequest.of(1, 10,
            Sort.by(Sort.Direction.ASC, "modification","serialNum"));
    public MiServiceImpl measurementInstrumentService =
            new MiServiceImpl(miRepository);


    @Test
    @DisplayName("Test findById if mi not found")
    public void testFindByIdIfMiNotFound() {
       long miId = 5L;
       when(miRepository.findById(miId)).thenReturn(Optional.empty());
       assertThrows(EntityNotFoundException.class, ()-> measurementInstrumentService.findById(miId));
    }

    @Test
    @DisplayName("Test findById if mi found")
    public void testFindByIdIfMiFound(){
        long miId = 5L;
        MeasurementInstrument mi = new MeasurementInstrument();
        mi.setId(miId);
        when(miRepository.findById(miId)).thenReturn(Optional.of(mi));
        MiFullDto miFullDto = measurementInstrumentService.findById(miId);
        assertEquals(miId, miFullDto.getId());
    }

    @Test
    @DisplayName("Test getById if mi not found")
    public void testGetByIdIfMiNotFound(){
        long miId = 5L;
        when(miRepository.findById(miId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()-> measurementInstrumentService.getMiById(miId));
    }

    @Test
    @DisplayName("Test getById if mi found")
    public void testGetByIdIfMiFound(){
        long miId = 5L;
        MeasurementInstrument expectedMi = new MeasurementInstrument();
        expectedMi.setId(miId);
        when(miRepository.findById(miId)).thenReturn(Optional.of(expectedMi));
        MeasurementInstrument actualMi = measurementInstrumentService.getMiById(miId);
        assertEquals(expectedMi, actualMi);
    }

    @Test
    @DisplayName("Test findBySearchString pageable")
    public void testFindBySearchStringPageable(){
        String searchString = "search";
        MeasurementInstrument mi = new MeasurementInstrument();
        mi.setId(3L);
        Organization organization = new Organization();
        organization.setNotation("Owner");
        mi.setOwner(organization);
        Page<MeasurementInstrument> page = new PageImpl<>(List.of(mi),pageable, 1L);
        when(miRepository.findByModificationIgnoreCaseContainingOrSerialNumIgnoreCaseContaining(
                searchString, searchString, pageable)).thenReturn(page);
        Page<MiDto> actualPage = measurementInstrumentService.findBySearchString(searchString, pageable);
        assertEquals(page.getContent().size(), actualPage.getContent().size());
    }

    @Test
    @DisplayName("Test findBySearchString")
    public void testFindBySearchString(){
        String searchString = "search";
        MeasurementInstrument mi = new MeasurementInstrument();
        mi.setId(5L);
        Organization organization = new Organization();
        organization.setNotation("Owner");
        mi.setOwner(organization);
        when(miRepository.findByModificationIgnoreCaseContainingOrSerialNumIgnoreCaseContaining(searchString, searchString))
                .thenReturn(List.of(mi));
        List<MiDto> actualList = measurementInstrumentService.findBySearchString(searchString);
        assertEquals(mi.getId(), actualList.get(0).getId());
    }

    @Test
    @DisplayName("Test findAll pageable")
    public void testFindAllPageable(){
        Organization organization = new Organization();
        organization.setNotation("Owner");
        MeasurementInstrument mi = new MeasurementInstrument();
        mi.setOwner(organization);
        Page<MeasurementInstrument> expectedPage  = new PageImpl<>(List.of(mi), pageable,1L);
        when(miRepository.findAll(pageable)).thenReturn(expectedPage);
        Page<MiDto> actualPage = measurementInstrumentService.findAll(pageable);
        assertEquals(mi.getOwner().getNotation(), actualPage.getContent().get(0).getOwner());
    }

    @Test
    @DisplayName("Test findAll")
    public void testFindAll(){
        Organization organization = new Organization();
        organization.setNotation("Owner");
        MeasurementInstrument mi = new MeasurementInstrument();
        mi.setOwner(organization);
        when(miRepository.findAll()).thenReturn(List.of(mi));
        List<MiDto> actualList = measurementInstrumentService.findAll();
        assertEquals(mi.getOwner().getNotation(), actualList.get(0).getOwner());
    }

    @Test
    @DisplayName("Test deleteById")
    public void testDeleteById(){
        long miId = 2L;
        measurementInstrumentService.deleteById(miId);
        verify(miRepository, times(1)).deleteById(miId);
    }

}