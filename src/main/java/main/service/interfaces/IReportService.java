package main.service.interfaces;

import main.dto.verification.RecordDto;
import main.dto.verification.ReportDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IReportService {
    ResponseEntity<?> createReport(ReportDto reportDto);
    ResponseEntity<?> updateRecord(RecordDto recordDto);
    ResponseEntity<?> findById(long id);
    ResponseEntity<?> findRecordById(long id);
    List<ReportDto> findAll();
    ResponseEntity<?> deleteReport(long id);


}
