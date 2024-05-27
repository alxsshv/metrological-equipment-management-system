package main.service.interfaces;

import main.dto.VerificationRecordDto;
import org.springframework.http.ResponseEntity;

public interface IVerificationRecordService {
    ResponseEntity<?> getById(long id);
    ResponseEntity<?> update(VerificationRecordDto recordDto);
}
