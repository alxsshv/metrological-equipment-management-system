package main.controller;


import main.dto.VerificationRecordDto;
import main.dto.VerificationIssueDto;
import main.service.verificationissue.VerificationIssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("verificationIssue")
public class VerificationIssueController {
    @Autowired
    VerificationIssueService verificationIssueService;


    @GetMapping
    public List<VerificationIssueDto> getIssuesList(){
        return verificationIssueService.findAll();
    }

    @PostMapping
    public ResponseEntity<?> addIssue(@RequestBody VerificationIssueDto issueDto)  {
        return verificationIssueService.addIssue(issueDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getIssueById(@PathVariable("id") String id){
        return verificationIssueService.findById(Integer.parseInt(id));
    }

    @GetMapping("/record/{id}")
    public ResponseEntity<?> getRecordById(@PathVariable("id") String id){
        return verificationIssueService.findRecordById(Integer.parseInt(id));
    }

    @PatchMapping("/record/{id}")
    public ResponseEntity<?> updateRecord(@RequestBody VerificationRecordDto recordDto){
        return verificationIssueService.updateRecord(recordDto);
    }


}
