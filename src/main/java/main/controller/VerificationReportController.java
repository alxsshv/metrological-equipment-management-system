package main.controller;

import main.dto.VerificationReportDto;
import main.service.implementations.VerificationReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("reports/verifications")
public class VerificationReportController {

    VerificationReportService verificationReportService;

    public VerificationReportController(VerificationReportService verificationReportService) {
        this.verificationReportService = verificationReportService;
    }

    @PostMapping
    public ResponseEntity<?> saveReport(@RequestBody VerificationReportDto reportDto){
        return verificationReportService.save(reportDto);
    }
}
