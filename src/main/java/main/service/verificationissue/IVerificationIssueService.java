package main.service.verificationissue;


import main.dto.VerificationRecordDto;
import main.dto.VerificationIssueDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IVerificationIssueService {
    ResponseEntity<?> addIssue(VerificationIssueDto issueDto);
    ResponseEntity<?> findById(int id);
    List<VerificationIssueDto> findAll();
    ResponseEntity<?> updateRecord(VerificationRecordDto updatingData);
    ResponseEntity<?> findRecordById(int id);
}
