package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import main.dto.rest.VerificationRecordDto;
import main.model.VerificationRecord;
import main.repository.VerificationRecordRepository;
import main.service.interfaces.VerificationRecordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Getter
@Setter
@AllArgsConstructor
public class VerificationRecordServiceTest {
    private final VerificationRecordRepository verificationRecordRepository = Mockito.mock(VerificationRecordRepository.class);
    private final VerificationRecordService verificationRecordService = new VerificationRecordServiceImpl(verificationRecordRepository);


    @Test
    @DisplayName("Test findById if entity not found")
    public void testFindByIdIfEntityNotFound(){
       long recordId = 1L;
       when(verificationRecordRepository.findById(recordId)).thenReturn(Optional.empty());
       assertThrows(EntityNotFoundException.class, ()-> verificationRecordService.findById(recordId));
     }

    @Test
    @DisplayName("Test findById if entity found")
    public void testFindByIdIfEntityFound(){
        long recordId = 3L;
        VerificationRecord verificationRecord = new VerificationRecord();
        verificationRecord.setId(recordId);
        when(verificationRecordRepository.findById(recordId)).thenReturn(Optional.of(verificationRecord));
        VerificationRecordDto verificationRecordDto = verificationRecordService.findById(recordId);
        assertEquals(recordId, verificationRecordDto.getId());
    }

    @Test
    @DisplayName("Test getRecordById if entity not found")
    public void testGetRecordByIdIfEntityNotFound(){
        long recordId = 3L;
        when(verificationRecordRepository.findById(recordId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()-> verificationRecordService.getRecordById(recordId));
    }

    @Test
    @DisplayName("Test getRecordById if entity found")
    public void testGetRecordByIdIfEntityFound(){
        long recordId = 1L;
        VerificationRecord expectedRecord = new VerificationRecord();
        when(verificationRecordRepository.findById(recordId)).thenReturn(Optional.of(expectedRecord));
        VerificationRecord actualRecord = verificationRecordService.getRecordById(recordId);
        assertEquals(expectedRecord , actualRecord);
        verify(verificationRecordRepository,times(1)).findById(recordId);
    }

    @Test
    @DisplayName("Test update if entity found")
    public void testUpdateIfEntityFound(){
        long recordId = 3L;
        VerificationRecordDto recordDto = new VerificationRecordDto();
        recordDto.setId(recordId);
        when(verificationRecordRepository.findById(recordId)).thenReturn(Optional.of(new VerificationRecord()));
        verificationRecordService.update(recordDto);
        verify(verificationRecordRepository,times(1)).save(any(VerificationRecord.class));
    }

    @Test
    @DisplayName("Test update if record not found")
    public void testUpdateIfEntityNotFound(){
        long recordId = 1L;
        VerificationRecordDto recordDto = new VerificationRecordDto();
        when(verificationRecordRepository.findById(recordId)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class,()-> verificationRecordService.update(recordDto));
        verify(verificationRecordRepository,never()).save(any(VerificationRecord.class));
    }

    @Test
    @DisplayName("Test updateArshinVerificationNumber if record found")
    public void testUpdateArshinVerificationNumberIfRecordFound(){
        long id = 5L;
        when(verificationRecordRepository.findById(id)).thenReturn(Optional.of(new VerificationRecord()));
        verificationRecordService.updateArshinVerificationNumber(id,"12345");
        verify(verificationRecordRepository, times(1)).save(any(VerificationRecord.class));
    }

    @Test
    @DisplayName("Test updateArshinVerificationNumber if record not found")
    public void testUpdateArshinVerificationNumberIfRecordNotFound(){
        long id = 5L;
        when(verificationRecordRepository.findById(id)).thenReturn(Optional.of(new VerificationRecord()));
        verificationRecordService.updateArshinVerificationNumber(id,"12345");
        verify(verificationRecordRepository, times(1)).save(any(VerificationRecord.class));
    }


    @Test
    @DisplayName("Test delete")
    public void testDelete(){
        long recordId = 1L;
        verificationRecordService.delete(recordId);
        verify(verificationRecordRepository,times(1)).deleteById(recordId);
    }

    @Test
    @DisplayName("Test findVerificationAmountForEveryDateByEmployeeId")
    public void testFindVerificationAmountForEveryDateByEmployeeId(){
        long employeeId = 5L;
        int pageNum = 1;
        int pageSize = 10;
        Pageable pageable = PageRequest.of(pageNum,pageSize, Sort.by(Sort.Direction.ASC,"verificationDate"));
        Page<Map<String, Integer>> page = new PageImpl<>(List.of(Map.of("02.10.2024",10,"01.11.2024",5)),pageable,1);
        when(verificationRecordRepository.findVerificationAmountForEveryDateByEmployeeId(employeeId, pageable))
                .thenReturn(page);
        Page<Map<String,Integer>> counters = verificationRecordService.findVerificationAmountForEveryDateByEmployeeId(employeeId, pageable);
        assertEquals(page.getContent().get(0),counters.getContent().get(0));
    }
}
