package main.service.xml;

import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface IXMLService {

   ResponseEntity<?> getFsaXMLForIssue(String id) throws IOException;
}
