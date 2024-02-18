package main.service.verificationissue;


import main.dto.RecordDto;
import main.dto.VerificationIssueDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IVerificationIssueService {
    ResponseEntity<?> addIssue(VerificationIssueDto issueDto);
    ResponseEntity<?> findById(int id);
    List<VerificationIssueDto> findAll();
    ResponseEntity<?> updateRecord(RecordDto updatingData);
    ResponseEntity<?> findRecordById(int id);
}
