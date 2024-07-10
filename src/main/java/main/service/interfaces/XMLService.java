package main.service.interfaces;

import org.springframework.http.ResponseEntity;

public interface XMLService {
    ResponseEntity<?> getXMLFileForArshinByReport(long reportId);
    ResponseEntity<?> getXMLFileForArshinByReadyToSendReports();
    ResponseEntity<?> getXMLFileForFSAByReportId(long reportId);
    ResponseEntity<?> getXMLFileForFSAByPublicToArshinReports();
}
