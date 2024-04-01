package main.service.interfaces;


import main.dto.VerificationRecordDto;
import main.dto.VerificationIssueDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IVerificationIssueService {
    ResponseEntity<?> addIssue(VerificationIssueDto issueDto);
    ResponseEntity<?> findById(long id);
    List<VerificationIssueDto> findAll();
    ResponseEntity<?> updateRecord(VerificationRecordDto updatingData);
    ResponseEntity<?> findRecordById(long id);
}
