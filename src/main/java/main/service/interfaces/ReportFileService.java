package main.service.interfaces;

import main.dto.rest.ReportFileDto;
import main.model.ReportFile;
import main.model.VerificationReport;
import main.service.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;

public interface ReportFileService {
    String addReportFile(Category category, List<VerificationReport> verificationReportList);
    ReportFile findFileById(long id);
    ResponseEntity<?> getReportFile(Long id);
    ResponseEntity<?> getReportFile(String fileName);
    Page<ReportFileDto> getReports(Category category, Pageable pageable);
    void delete(long id) throws IOException;
}
