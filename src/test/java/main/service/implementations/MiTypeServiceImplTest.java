package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import main.dto.rest.MiTypeDto;
import main.model.MiType;
import main.repository.MiTypeRepository;
import main.service.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.*;


import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class MiTypeServiceImplTest {
    private final MiTypeRepository miTypeRepository =
            Mockito.mock(MiTypeRepository.class);
    private final FileServiceImpl fileService = Mockito.mock(FileServiceImpl.class);
    private final Pageable pageable = PageRequest.of(0,10, Sort.by(Sort.Direction.ASC,"number"));
    private final MiTypeServiceImpl miTypeService =
            new MiTypeServiceImpl(miTypeRepository, fileService);


    @Test
    @DisplayName("Test findByNumber if MiType found")
    public void testFindByNumberIfMiTypeFound() {
        String number = "number";
        MiType miType = new MiType();
        miType.setNumber(number);
        when(miTypeRepository.findByNumber(number)).thenReturn(miType);
        MiTypeDto miTypeDto = miTypeService.findByNumber(number);
        assertEquals(miTypeDto.getNumber(), miType.getNumber());
    }

    @Test
    @DisplayName("Test findByNumber if MiType not found")
    public void testFindByNumberIfMiTypeNotFound() {
        String number = "number";
        when(miTypeRepository.findByNumber(number)).thenReturn(null);
        assertThrows(EntityNotFoundException.class, ()-> miTypeService.findByNumber(number));
    }

    @Test
    @DisplayName("Test findBySearchString pageable")
    public void testFindBySearchStringPageable(){
        String searchString = "string";
        Page<MiType> page = new PageImpl<>(List.of(new MiType(), new MiType()),pageable, 1L);
        when(miTypeRepository
                .findByNumberContainingOrTitleIgnoreCaseContainingOrNotationIgnoreCaseContaining(searchString,
                        searchString, searchString, pageable)).thenReturn(page);
        Page<MiTypeDto> dtoPage = miTypeService.findBySearchString(searchString, pageable);
        assertEquals(2, dtoPage.getContent().size());
    }

    @Test
    @DisplayName("Test findBySearchString")
    public void testFindBySearchString(){
        String searchString = "string";
        when(miTypeRepository
                .findByNumberContainingOrTitleIgnoreCaseContainingOrNotationIgnoreCaseContaining(searchString
                        ,searchString,searchString)).thenReturn(List.of(new MiType(), new MiType()));
        List<MiTypeDto> dtoList = miTypeService.findBySearchString(searchString);
        assertEquals(2, dtoList.size());
    }


    @Test
    @DisplayName("Test FindAll pageable")
    public void testFindAllPageable(){
        Page<MiType> miTypePage = new PageImpl<>(List.of(new MiType(), new MiType()),pageable, 1L);
        when(miTypeRepository.findAll(pageable)).thenReturn(miTypePage);
        Page<MiTypeDto> dtoPage = miTypeService.findAll(pageable);
        assertEquals(miTypePage.getContent().size(), dtoPage.getContent().size());
    }

    @Test
    @DisplayName("Test findAll")
    public void testFindAll(){
        when(miTypeRepository.findAll()).thenReturn(List.of(new MiType(), new MiType(), new MiType()));
        List<MiTypeDto> dtoList = miTypeService.findAll();
        assertEquals(3, dtoList.size());
    }

    @Test
    @DisplayName("Test deleteById")
    public void testDeleteByID() throws IOException {
        long id = 1L;
        miTypeService.deleteById(id);
        verify(miTypeRepository,times(1)).deleteById(id);
        verify(fileService, times(1)).deleteAllFiles(Category.MI_TYPE,id);
    }
}







