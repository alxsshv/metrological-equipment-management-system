package main.service.implementations;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import main.dto.VerificationRecordDto;
import main.dto.mappers.VerificationRecordDtoMapper;
import main.model.VerificationRecord;
import main.repository.VerificationRecordRepository;
import main.service.ServiceMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VerificationRecordService {
    private static final Logger logger = LoggerFactory.getLogger(VerificationRecordService.class);
    private VerificationRecordRepository recordRepository;

    public ResponseEntity<?> getById(long id){
        Optional<VerificationRecord> recordOpt = recordRepository.findById(id);
        if (recordOpt.isEmpty()){
           String errorMessage = "Запись о поверке № " + id + " не найдена";
           logger.info(errorMessage);
           return  ResponseEntity.status(404).body(new ServiceMessage(errorMessage));
        }
        VerificationRecordDto recordDto = VerificationRecordDtoMapper.mapToDto(recordOpt.get());
        return ResponseEntity.ok(recordDto);
    }
}
