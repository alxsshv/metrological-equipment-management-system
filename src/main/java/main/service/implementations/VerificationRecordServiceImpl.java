package main.service.implementations;


import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import main.dto.rest.VerificationRecordDto;
import main.dto.rest.mappers.VerificationRecordDtoMapper;
import main.model.VerificationRecord;
import main.repository.VerificationRecordRepository;
import main.service.ServiceMessage;
import main.service.interfaces.VerificationRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VerificationRecordServiceImpl implements VerificationRecordService {
    private static final Logger logger = LoggerFactory.getLogger(VerificationRecordServiceImpl.class);
    private VerificationRecordRepository recordRepository;


    @Override
    public ResponseEntity<?> findById(long id) {
        try {
            VerificationRecord record = getRecordById(id);
            VerificationRecordDto recordDto = VerificationRecordDtoMapper.mapToDto(record);
            return ResponseEntity.ok(recordDto);
        } catch (EntityNotFoundException ex){
            logger.info(ex.getMessage());
            return ResponseEntity.status(404).body(new ServiceMessage(ex.getMessage()));
        }

    }

    @Override
    public VerificationRecord getRecordById(long id){
        Optional<VerificationRecord> recordOpt = recordRepository.findById(id);
        if (recordOpt.isEmpty()) {
            throw new EntityNotFoundException("Запись о поверке № " + id + " не найдена");
        }
        return recordOpt.get();
    }

    @Override
    public ResponseEntity<?> update(VerificationRecordDto recordDto) {
        try {
            VerificationRecord updateRecord = getRecordById(recordDto.getId());
            updateRecord.updateFrom(VerificationRecordDtoMapper.mapToEntity(recordDto));
            recordRepository.save(updateRecord);
            String okMessage = "Запись о поверке № " + updateRecord.getId() + " успешно обновлена";
            logger.info(okMessage);
            return ResponseEntity.ok().body(new ServiceMessage(okMessage));
        } catch (EntityNotFoundException ex){
            logger.error(ex.getMessage());
            return ResponseEntity.status(404).body(new ServiceMessage(ex.getMessage()));
        }
    }

    public void updateArshinVerificationNumber(long recordId, String arshinVerificationNumber) {
        try {
            VerificationRecord updateRecord = getRecordById(recordId);
            updateRecord.setArshinVerificationNumber(arshinVerificationNumber);
            recordRepository.save(updateRecord);
            logger.info("Номер в ФГИС Аршин записи о поверке  № {} успешно обновлен", recordId);
           } catch (EntityNotFoundException ex){
            logger.error(ex.getMessage());
           }
    }


    @Override
    public ResponseEntity<?> delete(long id) {
        recordRepository.deleteById(id);
        String okMessage = "Запись о поверке № " + id + " удалена";
        logger.info(okMessage);
        return ResponseEntity.ok().body(new ServiceMessage(okMessage));
    }

    @Override
    public ResponseEntity<?> findVerificationAmountForEveryDateByEmployeeId(long employeeId, Pageable pageable){
        Page<Map<String,Integer>> counters = recordRepository.findVerificationAmountForEveryDateByEmployeeId(employeeId, pageable);
        return ResponseEntity.ok().body(counters);
    }


}
