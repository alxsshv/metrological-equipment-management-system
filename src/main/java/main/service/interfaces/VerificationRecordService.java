package main.service.interfaces;

import main.dto.rest.VerificationRecordDto;
import main.model.VerificationRecord;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;



public interface VerificationRecordService {
    ResponseEntity<?> findById(long id);
    VerificationRecord getRecordById(long id);
    ResponseEntity<?> update(VerificationRecordDto recordDto);
    ResponseEntity<?> delete(long id);
    ResponseEntity<?> findVerificationAmountForEveryDateByEmployeeId(long employeeId, Pageable pageable);
}
