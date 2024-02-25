package main.service.verification;

import main.dto.verification.RecordDto;
import main.dto.verification.ReportDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IReportService {
    ResponseEntity<?> addIssue(ReportDto reportDto);
    ResponseEntity<?> updateRecord(RecordDto recordDto);
    ResponseEntity<?> findById(int id);
    ResponseEntity<?> findRecordById(int id);
    List<ReportDto> findAll();
    ResponseEntity<?> deleteReport(int id);


}
