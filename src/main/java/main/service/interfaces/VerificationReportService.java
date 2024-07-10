package main.service.interfaces;

import main.dto.rest.VerificationReportDto;
import main.dto.rest.VerificationReportFullDto;
import main.model.VerificationReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface VerificationReportService {
    ResponseEntity<?> save(VerificationReportFullDto reportFullDto);
    ResponseEntity<?> findById (long id);
    VerificationReport getReportById(long id);
    List<VerificationReport> getReadyToSendReports();
    List<VerificationReport> getPublicToArshinReportsAndNotSendToFsa();
    ResponseEntity<?> update (VerificationReportFullDto reportFullDto);
    ResponseEntity<?> updateReportFromArshin(long id);
    ResponseEntity<?> delete (long id);
    Page<VerificationReportDto> findAll(Pageable pageable);
}
