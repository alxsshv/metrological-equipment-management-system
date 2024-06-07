package main.service.interfaces;

import main.dto.rest.VerificationRecordDto;
import org.springframework.http.ResponseEntity;

public interface IVerificationRecordService {
    ResponseEntity<?> getById(long id);
    ResponseEntity<?> update(VerificationRecordDto recordDto);
}
