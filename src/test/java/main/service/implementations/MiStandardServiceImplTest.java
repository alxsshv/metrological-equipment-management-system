package main.service.implementations;



import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintValidatorContext;
import main.dto.rest.*;
import main.model.*;
import main.repository.MiStandardRepository;
import main.service.interfaces.MiDetailsService;
import main.service.validators.MiStandardAlreadyExistValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class MiStandardServiceImplTest {
    private final MiStandardRepository miStandardRepository = Mockito.mock(MiStandardRepository.class);
    private final MiDetailsService miDetailsService = Mockito.mock(MiDetailsService.class);
    private final FileServiceImpl fileService = Mockito.mock(FileServiceImpl.class);
    private final MiStandardServiceImpl miStandardService = new MiStandardServiceImpl(miStandardRepository,miDetailsService,fileService);
    private final MultipartFile[] files = {};
    private final String[] descriptions = {};
    private ConstraintValidatorContext constraintValidatorContext;

    @Test
    @DisplayName("Test save if parent measurement instrument is null")
    public void testSaveIfParentMeasurementInstrumentIsNull(){
        long miId = 3L;
        MiDetailsDto miDetailsDto = new MiDetailsDto();
        miDetailsDto.setId(miId);
        long miStandardId = 5L;
        MiStandardDto miStandardDto = new MiStandardDto();
        miStandardDto.setId(miStandardId);
        miStandardDto.setMiDetailsDto(miDetailsDto);
        when(miDetailsService.getById(miId)).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class,()-> miStandardService.save(miStandardDto, files, descriptions));
    }


    @Test
    @DisplayName("Test save if miStandard already exist")
    public void testSaveIfMiStandardAlreadyExist() {
        long miStandardId = 1L;
        String arshinNum = "arshin";
        MiStandardDto miStandardDto = new MiStandardDto();
        miStandardDto.setId(miStandardId);
        miStandardDto.setArshinNumber(arshinNum);
        when(miStandardRepository.findByArshinNumber(arshinNum)).thenReturn(new MiStandard());
        boolean alreadyExist = !(new MiStandardAlreadyExistValidator(miStandardRepository).isValid(miStandardDto, constraintValidatorContext));
        assertTrue(alreadyExist);
    }

    @Test
    @DisplayName("Test save if miStandard not exist")
    public void testSaveIfMiStandardNotExist() {
        long miStandardId = 1L;
        String arshinNum = "arshin";
        MiStandardDto miStandardDto = new MiStandardDto();
        miStandardDto.setId(miStandardId);
        miStandardDto.setArshinNumber(arshinNum);
        when(miStandardRepository.findByArshinNumber(arshinNum)).thenReturn(null);
        boolean notExist = new MiStandardAlreadyExistValidator(miStandardRepository).isValid(miStandardDto, constraintValidatorContext);
        assertTrue(notExist);
    }


    @Test
    @DisplayName("Test findById if standard not found")
    public void testFindByIdIfStandardNotFound(){
        long id = 5L;
        when(miStandardRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, ()-> miStandardService.findById(id));
    }

    @Test
    @DisplayName("Test getMiStandardById if standard found")
    public void testGetMiStandardByIdIfStandardFound(){
        long id = 5L;
        MiStandard expectedMiStandard = new MiStandard();
        expectedMiStandard.setId(id);
        when(miStandardRepository.findById(id)).thenReturn(Optional.of(expectedMiStandard));
        MiStandard actualMiStandard = miStandardService.getMiStandardById(id);
        assertEquals(expectedMiStandard.getId(), actualMiStandard.getId());
    }

    @Test
    @DisplayName("Test getMiStandardById if standard not found")
    public void testGetMiStandardByIdIFStandardNotFound(){
        long id = 5L;
        when(miStandardRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, ()->miStandardService.getMiStandardById(id));
    }

    @Test
    @DisplayName("Test delete")
    public void testDelete(){
        long id = 3L;
        miStandardService.delete(id);
        verify(miStandardRepository, times(1)).deleteById(id);
    }


}
