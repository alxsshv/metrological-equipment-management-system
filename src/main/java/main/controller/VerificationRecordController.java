package main.controller;

import main.dto.rest.VerificationRecordDto;
import main.service.implementations.VerificationRecordService;
import main.service.interfaces.IVerificationRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/verifications/records")
public class VerificationRecordController {

    private final IVerificationRecordService recordService;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVerificationRecord(@PathVariable ("id") String id){
        return recordService.delete(Long.parseLong(id));
    }
}
