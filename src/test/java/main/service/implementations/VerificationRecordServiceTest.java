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
import org.springframework.http.ResponseEntity;
import testutils.TestEntityGenerator;

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
       ResponseEntity<?> responseEntity = verificationRecordService.findById(recordId);
       assertEquals(404,responseEntity.getStatusCode().value());
       verify(verificationRecordRepository,times(1)).findById(recordId);
     }

    @Test
    @DisplayName("Test findById if entity found")
    public void testFindByIdIfEntityFound(){
        long recordId = 1L;
        when(verificationRecordRepository.findById(recordId)).thenReturn(Optional.of(new VerificationRecord()));
        ResponseEntity<?> responseEntity = verificationRecordService.findById(recordId);
        assertEquals(200,responseEntity.getStatusCode().value());
        verify(verificationRecordRepository,times(1)).findById(recordId);
    }

    @Test
    @DisplayName("Test getRecordById if entity not found")
    public void testGetRecordByIdIfEntityNotFound(){
        long recordId = 1L;
        when(verificationRecordRepository.findById(recordId)).thenReturn(Optional.empty());
        Exception exception = assertThrows(EntityNotFoundException.class,()-> verificationRecordService.getRecordById(recordId));
        assertEquals("Запись о поверке № 1 не найдена" , exception.getMessage());
        verify(verificationRecordRepository,times(1)).findById(recordId);
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
        long recordId = 1L;
        VerificationRecordDto recordDto = TestEntityGenerator.generateVerificationRecordDto(recordId);
        recordDto.setArshinVerificationNumber("testnumber");
        VerificationRecord recordfromDb = TestEntityGenerator.genereteVerificationRecord(recordId);
        when(verificationRecordRepository.findById(recordId)).thenReturn(Optional.of(recordfromDb));
        ResponseEntity<?> responseEntity = verificationRecordService.update(recordDto);
        assertEquals(200 , responseEntity.getStatusCode().value());
        verify(verificationRecordRepository,times(1)).findById(recordId);
    }

    @Test
    @DisplayName("Test update if entity not found")
    public void testUpdateIfEntityNotFound(){
        long recordId = 1L;
        VerificationRecordDto recordDto = TestEntityGenerator.generateVerificationRecordDto(recordId);
        when(verificationRecordRepository.findById(recordId)).thenReturn(Optional.empty());
        ResponseEntity<?> responseEntity = verificationRecordService.update(recordDto);
        assertEquals(404 , responseEntity.getStatusCode().value());
        verify(verificationRecordRepository,times(1)).findById(recordId);
    }

    @Test
    @DisplayName("Test delete")
    public void testDelete(){
        long recordId = 1L;
        ResponseEntity<?> responseEntity = verificationRecordService.delete(recordId);
        assertEquals(200 , responseEntity.getStatusCode().value());
        verify(verificationRecordRepository,times(1)).deleteById(recordId);
    }
}
