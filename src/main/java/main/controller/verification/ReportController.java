package main.controller.verification;

import main.dto.verification.RecordDto;
import main.dto.verification.ReportDto;
import main.service.interfaces.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Autowired
    IReportService reportService;

    @GetMapping
    public List<ReportDto> getReportList(){
        return reportService.findAll();
    }

    //TODO Добавить проверки вводимых полей в View
    @PostMapping
    public ResponseEntity<?> createReport(@RequestBody ReportDto reportDto)  {
        return  reportService.createReport(reportDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReportById(@PathVariable("id") String id){
        return reportService.findById(Integer.parseInt(id));
    }

    @GetMapping("/record/{id}")
    public ResponseEntity<?> getRecordById(@PathVariable("id") String id){
        return reportService.findRecordById(Integer.parseInt(id));
    }

    @PatchMapping("/record/{id}")
    public ResponseEntity<?> updateRecord(@RequestBody RecordDto recordDto){
        return reportService.updateRecord(recordDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReport(@PathVariable("id") String id){
        return reportService.deleteReport(Integer.parseInt(id));
    }
}
