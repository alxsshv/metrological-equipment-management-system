package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import main.dto.rest.MiTypeDto;
import main.dto.rest.MiTypeFullDto;
import main.dto.rest.mappers.MiTypeDtoMapper;
import main.model.MiType;
import main.model.MiTypeInstruction;
import main.repository.MiTypeInstructionRepository;
import main.repository.MiTypeModificationRepository;
import main.repository.MiTypeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static testutils.TestEntityGenerator.*;

public class MiTypeServiceTest {
    private final MiTypeRepository miTypeRepository =
            Mockito.mock(MiTypeRepository.class);
    private final MiTypeInstructionRepository miTypeInstructionRepository =
            Mockito.mock(MiTypeInstructionRepository.class);
    private final MiTypeModificationRepository miTypeModificationRepository =
            Mockito.mock(MiTypeModificationRepository.class);
    private final FileServiceImpl fileService = Mockito.mock(FileServiceImpl.class);
    private final Pageable pageable = PageRequest.of(0,10, Sort.by(Sort.Direction.ASC,"number"));
    private final MiTypeServiceImpl miTypeService =
            new MiTypeServiceImpl(miTypeRepository, miTypeInstructionRepository,
                    miTypeModificationRepository, fileService);
    private final MultipartFile[] files = {};
    private final String[] descriptions = {};

    @Test
    @DisplayName("Test save if miType already exists")
    public void testSaveIfMiTypeAlreadyExists() throws IOException {
        MiTypeFullDto miTypeFullDto = generateMiTypeFullDto(5L);
        when(miTypeRepository.findByNumber(miTypeFullDto.getNumber())).thenReturn(new MiType());
        ResponseEntity<?> responseEntity = miTypeService.save(miTypeFullDto, files, descriptions);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(miTypeRepository,times(1)).findByNumber(miTypeFullDto.getNumber());
        verify(miTypeInstructionRepository,never()).save(any(MiTypeInstruction.class));
    }

    @Test
    @DisplayName("Test save if number is not corrected")
    public void testSaveIfMiTypeNumberIsNotCorrected() throws IOException {
        MiTypeFullDto miTypeFullDto = generateMiTypeFullDto(5L);
        miTypeFullDto.setNumber("");
        when(miTypeRepository.findByNumber(miTypeFullDto.getNumber())).thenReturn(new MiType());
        ResponseEntity<?> responseEntity = miTypeService.save(miTypeFullDto, files, descriptions);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(miTypeRepository, never()).findByNumber(miTypeFullDto.getNumber());
        verify(miTypeInstructionRepository, never()).save(any(MiTypeInstruction.class));
    }

    @Test
    @DisplayName("Test save if title is null")
    public void testSaveIfMiTypeTitleIsNull() throws IOException {
        MiTypeFullDto miTypeDto = generateMiTypeFullDto(5L);
        miTypeDto.setTitle(null);
        when(miTypeRepository.findByNumber(miTypeDto.getNumber())).thenReturn(new MiType());
        ResponseEntity<?> responseEntity = miTypeService.save(miTypeDto, files,descriptions);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(miTypeRepository, never()).findByNumber(miTypeDto.getNumber());
        verify(miTypeInstructionRepository, never()).save(any(MiTypeInstruction.class));
    }

    @Test
    @DisplayName("Test save if modifications is null")
    public void testSaveIfMiTypeModificationsIsNull() throws IOException {
        MiTypeFullDto miTypeDto = generateMiTypeFullDto(5L);
        miTypeDto.setModifications(new ArrayList<>());
        when(miTypeRepository.findByNumber(miTypeDto.getNumber())).thenReturn(new MiType());
        ResponseEntity<?> responseEntity = miTypeService.save(miTypeDto, files, descriptions);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(miTypeRepository, never()).findByNumber(miTypeDto.getNumber());
        verify(miTypeInstructionRepository, never()).save(any(MiTypeInstruction.class));
    }

    @Test
    @DisplayName("Test save if created new MiType")
    public void testSaveIfCreatedNewMiType() throws IOException {
        MiTypeFullDto miTypeFullDto = generateMiTypeFullDto(5L);
        when(miTypeRepository.findByNumber(miTypeFullDto.getNumber())).thenReturn(null);
        MiTypeInstruction miTypeInstruction = MiTypeDtoMapper.mapToEntity(miTypeFullDto);
        miTypeInstruction.setId(miTypeFullDto.getId());
        when(miTypeInstructionRepository.save(any(MiTypeInstruction.class))).thenReturn(miTypeInstruction);
        ResponseEntity<?> responseEntity = miTypeService.save(miTypeFullDto, files, descriptions);
        assertEquals("201 CREATED", responseEntity.getStatusCode().toString());
        verify(miTypeRepository,times(1)).findByNumber(miTypeFullDto.getNumber());
        verify(miTypeInstructionRepository,times(1)).save(any(MiTypeInstruction.class));
    }

    @Test
    @DisplayName("Test update if miType not found")
    public void testUpdateIfMiTypeNotFound(){
        long miTypeId = 5L;
        MiTypeFullDto miTypeDto = generateMiTypeFullDto(miTypeId);
        when(miTypeInstructionRepository.findById(miTypeId)).thenReturn(Optional.empty());
        ResponseEntity<?> responseEntity = miTypeService.update(miTypeDto);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(miTypeInstructionRepository,times(1)).findById(miTypeId);
        verify(miTypeInstructionRepository,never()).save(any(MiTypeInstruction.class));
    }

    @Test
    @DisplayName("Test update if modification is null")
    public void testUpdateIfMiTypeModificationIsNull() {
        long miTypeId = 5L;
        MiTypeFullDto miTypeDto = generateMiTypeFullDto(miTypeId);
        miTypeDto.setModifications(new ArrayList<>());
        MiTypeInstruction instruction = MiTypeDtoMapper.mapToEntity(miTypeDto);
        when(miTypeInstructionRepository.findById(miTypeId)).thenReturn(Optional.of(instruction));
        ResponseEntity<?> responseEntity = miTypeService.update(miTypeDto);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
        verify(miTypeInstructionRepository, never()).findById(miTypeId);
        verify(miTypeInstructionRepository, never()).save(any(MiTypeInstruction.class));
    }

    @Test
    @DisplayName("Test delete if miType found")
    public void testDeleteIfMiTypeFound() throws IOException {
        long miTypeId = 5L;
        MiTypeFullDto miTypeDto = generateMiTypeFullDto(miTypeId);
        MiTypeInstruction instruction = MiTypeDtoMapper.mapToEntity(miTypeDto);
        when(miTypeInstructionRepository.findById(miTypeId)).thenReturn(Optional.of(instruction));
        ResponseEntity<?> responseEntity = miTypeService.delete(miTypeId);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
        verify(miTypeRepository,times(1)).deleteById(miTypeId);
    }

    @Test
    @DisplayName("Test delete if miType not found")
    public void testDeleteIfMiTypeNotFound() throws IOException {
        long miTypeId = 5L;
        when(miTypeInstructionRepository.findById(miTypeId)).thenReturn(Optional.empty());
        ResponseEntity<?> responseEntity = miTypeService.delete(miTypeId);
        assertEquals("404 NOT_FOUND", responseEntity.getStatusCode().toString());
        verify(miTypeRepository,never()).deleteById(miTypeId);
    }

    @Test
    @DisplayName("Test findById if miType found")
    public void testFindByIDIfMiTypeFound() {
        long miTypeId = 5L;
        MiTypeInstruction instruction = generateMiTypeInstruction(miTypeId);
        when(miTypeInstructionRepository.findById(miTypeId)).thenReturn(Optional.of(instruction));
        ResponseEntity<?> responseEntity = miTypeService.findById(miTypeId);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
        verify(miTypeInstructionRepository,times(1)).findById(miTypeId);
    }

    @Test
    @DisplayName("Test findById if miType not found")
    public void testFindByIdIfMiTypeNotFound() {
        long miTypeId = 5L;
        when(miTypeRepository.findById(miTypeId)).thenReturn(Optional.empty());
        ResponseEntity<?> responseEntity = miTypeService.findById(miTypeId);
        assertEquals("404 NOT_FOUND", responseEntity.getStatusCode().toString());
    }

    @Test
    @DisplayName("test getInstructionById if entity not found")
    public void testGetInstructionByIdIfEntityNotFound() throws EntityNotFoundException {
        long miTypeId = 1L;
        when(miTypeInstructionRepository.findById(miTypeId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class,
                ()-> miTypeService.getInstructionById(miTypeId)) ;
        assertEquals("Запись о типе СИ № 1 не найдена",exception.getMessage());
        verify(miTypeInstructionRepository,times(1)).findById(miTypeId);
    }

    @Test
    @DisplayName("test getInstructionById if entity found")
    public void testGetInstructionByIdIfEntityFound() throws EntityNotFoundException {
        long miTypeId = 1L;
        MiTypeInstruction expectedInstruction = generateMiTypeInstruction(miTypeId);
        when(miTypeInstructionRepository.findById(miTypeId)).thenReturn(Optional.of(expectedInstruction));
        MiTypeInstruction actualInstruction = miTypeService.getInstructionById(miTypeId);
        assertEquals(expectedInstruction, actualInstruction);
        verify(miTypeInstructionRepository,times(1)).findById(miTypeId);
    }


    @Test
    @DisplayName("Test findBySearchString if searchString is empty")
    public void testFindBySearchStringIfSearcStringIsEmpty() {
        String searchString = "";
        when(miTypeRepository
                .findByNumberContainingOrTitleContainingOrNotationContaining(searchString,searchString,searchString, pageable))
                .thenReturn(null);
        ResponseEntity<?> responseEntity = miTypeService.findBySearchString(searchString, pageable);
        assertEquals("400 BAD_REQUEST", responseEntity.getStatusCode().toString());
    }

    @Test
    @DisplayName("Test findBySearchString if searchString is not empty")
    public void testFindBySearchStringIfMiTypeNotFound() {
        long totalPages = 1L;
        String searchString = "В7-78";
        MiType miType = generateMiTypeInstruction(1L).getMiType();
        List <MiType> findedMiTypes = List.of(miType);
        Page<MiType> page = new PageImpl<>(findedMiTypes,pageable,totalPages);
        when(miTypeRepository
                .findByNumberContainingOrTitleContainingOrNotationContaining(searchString,searchString,searchString, pageable))
                .thenReturn(page);
        ResponseEntity<?> responseEntity = miTypeService.findBySearchString(searchString, pageable);
        assertEquals("200 OK", responseEntity.getStatusCode().toString());
        verify(miTypeRepository,times(1))
                .findByNumberContainingOrTitleContainingOrNotationContaining(searchString,searchString,searchString, pageable);
    }

    @Test
    @DisplayName("Test find All with pageable")
    public void testPageableFindAll(){
        int totalPages = 1;
        MiType miType = generateMiTypeInstruction(1L).getMiType();
        List <MiType> miTypes = List.of(miType);
        Page<MiType> page = new PageImpl<>(miTypes,pageable,totalPages);
        when(miTypeRepository.findAll(pageable)).thenReturn(page);
        Page<MiTypeDto> miTypeDtos = miTypeService.findAll(pageable);
        assertEquals(miTypes.size(),miTypeDtos.getContent().size());
        verify(miTypeRepository,times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Test find All without pageable")
    public void testFindAllWithoutPageable(){
        MiType miType1 = generateMiTypeInstruction(1L).getMiType();
        MiType miType2 = generateMiTypeInstruction(2L).getMiType();
        List <MiType> miTypes = List.of(miType1, miType2);
        when(miTypeRepository.findAll()).thenReturn(miTypes);
        List<MiTypeDto> miTypeDtos = miTypeService.findAll();
        assertEquals(miTypes.size(),miTypeDtos.size());
        verify(miTypeRepository,times(1)).findAll();
    }






}
