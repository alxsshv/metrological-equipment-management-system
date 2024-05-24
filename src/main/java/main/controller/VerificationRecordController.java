package main.controller;

import main.service.implementations.VerificationRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verifications/records")
public class VerificationRecordController {

    private final VerificationRecordService recordService;

    public VerificationRecordController(VerificationRecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVerificationRecord(@PathVariable ("id") String id){
        return recordService.getById(Long.parseLong(id));
    }
}
