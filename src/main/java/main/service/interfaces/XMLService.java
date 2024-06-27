package main.service.interfaces;

import org.springframework.http.ResponseEntity;

public interface XMLService {
    ResponseEntity<?> getXMLFileForArshin(long reportId);
    ResponseEntity<?> getXMLFileForFSA(long reportId);
}
