package main.service.interfaces;

import org.springframework.http.ResponseEntity;

public interface IXMLService {
    ResponseEntity<?> getXMLFileForArshin(long reportId);
    ResponseEntity<?> getXMLFileForFSA(long reportId);
}
