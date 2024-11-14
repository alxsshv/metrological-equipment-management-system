package main.service.implementations;


import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import main.dto.rest.VerificationRecordDto;
import main.dto.rest.mappers.VerificationRecordDtoMapper;
import main.model.VerificationRecord;
import main.repository.VerificationRecordRepository;
import main.service.interfaces.VerificationRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VerificationRecordServiceImpl implements VerificationRecordService {
    private static final Logger logger = LoggerFactory.getLogger(VerificationRecordServiceImpl.class);
    private VerificationRecordRepository recordRepository;


    @Override
    public VerificationRecordDto findById(long id) {
            VerificationRecord record = getRecordById(id);
            return VerificationRecordDtoMapper.mapToDto(record);
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
    public void update(VerificationRecordDto recordDto) {
            VerificationRecord updateRecord = getRecordById(recordDto.getId());
            updateRecord.updateFrom(VerificationRecordDtoMapper.mapToEntity(recordDto));
            recordRepository.save(updateRecord);
    }

    @Override
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
    public void delete(long id) {
        recordRepository.deleteById(id);
    }

    @Override
    public Page<Map<String, Integer>> findVerificationAmountForEveryDateByEmployeeId(long employeeId, Pageable pageable){
        return recordRepository.findVerificationAmountForEveryDateByEmployeeId(employeeId, pageable);
    }


}
