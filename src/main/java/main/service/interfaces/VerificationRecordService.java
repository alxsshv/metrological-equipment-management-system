package main.service.interfaces;

import main.dto.rest.VerificationRecordDto;
import main.model.VerificationRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;


public interface VerificationRecordService {
    VerificationRecordDto findById(long id);
    VerificationRecord getRecordById(long id);
    void update(VerificationRecordDto recordDto);
    void updateArshinVerificationNumber(long recordId, String arshinVerificationNumber);
    void delete(long id);
    Page<Map<String, Integer>> findVerificationAmountForEveryDateByEmployeeId(long employeeId, Pageable pageable);
}
