package main.controller;


import lombok.AllArgsConstructor;
import main.service.implementations.ArshinReportServiceFacade;
import main.service.implementations.FSAReportServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;


@RestController
@AllArgsConstructor
@RequestMapping("/xml")
public class XMLController {
    @Autowired
    private ArshinReportServiceFacade arshinReportServiceFacade;
    @Autowired
    private FSAReportServiceFacade fsaReportServiceFacade;

    @GetMapping("/arshin/{id}")
    public ResponseEntity<?> getArshinReportFile(@PathVariable ("id") long id) throws IOException {
        return arshinReportServiceFacade.createReportFileById(id);
    }

    @GetMapping("/arshin/ready")
    public ResponseEntity<?> getReadyToArshinSendReportFile() throws FileNotFoundException {
        return arshinReportServiceFacade.createReportFileForReadyToSentReports();
    }

    @GetMapping("/fsa/{id}")
    public ResponseEntity<?> getFsaReportFile(@PathVariable ("id") long id) throws IOException {
        return fsaReportServiceFacade.createReportFileById(id);
    }

    @GetMapping("/fsa/ready")
    public ResponseEntity<?> getReadyToFsaSendReportFile() throws FileNotFoundException {
        return fsaReportServiceFacade.createReportFileForPublicToArshinReports();
    }
}
