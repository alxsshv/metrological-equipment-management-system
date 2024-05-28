package main.service.implementations;

import lombok.AllArgsConstructor;
import main.dto.VerificationRecordDto;
import main.dto.mappers.VerificationRecordDtoMapper;
import main.model.VerificationRecord;
import main.repository.VerificationRecordRepository;
import main.service.ServiceMessage;
import main.service.interfaces.IVerificationRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VerificationRecordService implements IVerificationRecordService {
    private static final Logger logger = LoggerFactory.getLogger(VerificationRecordService.class);
    private VerificationRecordRepository recordRepository;
    private VerificationReportService reportService;

    @Override
    public ResponseEntity<?> getById(long id) {
        Optional<VerificationRecord> recordOpt = recordRepository.findById(id);
        if (recordOpt.isEmpty()) {
            String errorMessage = "Запись о поверке № " + id + " не найдена";
            logger.info(errorMessage);
            return ResponseEntity.status(404).body(new ServiceMessage(errorMessage));
        }
        VerificationRecordDto recordDto = VerificationRecordDtoMapper.mapToDto(recordOpt.get());
        return ResponseEntity.ok(recordDto);
    }

    @Override
    public ResponseEntity<?> update(VerificationRecordDto recordDto) {
        Optional<VerificationRecord> recordOpt = recordRepository.findById(recordDto.getId());
        if (recordOpt.isEmpty()) {
            String errorMessage = "Запись о поверке № " + recordDto.getId() + " не найдена";
            logger.info(errorMessage);
            return ResponseEntity.status(404).body(new ServiceMessage(errorMessage));
        }
        VerificationRecord updateRecord = recordOpt.get();
        updateRecord.updateFrom(VerificationRecordDtoMapper.mapToEntity(recordDto));
        recordRepository.save(updateRecord);
        String okMessage = "Запись о поверке № " + updateRecord.getId() + " успешно обновлена";
        logger.info(okMessage);
        return ResponseEntity.ok().body(new ServiceMessage(okMessage));
    }

    public ResponseEntity<?> delete(long id) {
        recordRepository.deleteById(id);
        String okMessage = "Запись о поверке № " + id + " удалена";
        logger.info(okMessage);
        return ResponseEntity.ok().body(new ServiceMessage(okMessage));
    }
}
