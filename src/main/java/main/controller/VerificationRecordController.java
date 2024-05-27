package main.controller;

import main.dto.VerificationRecordDto;
import main.service.implementations.VerificationRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVerificationRecord(@RequestBody VerificationRecordDto recordDto){
        return recordService.update(recordDto);
    }
}
