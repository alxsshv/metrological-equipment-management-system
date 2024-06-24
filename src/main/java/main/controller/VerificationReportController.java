package main.controller;

import lombok.AllArgsConstructor;
import main.config.AppConstants;
import main.dto.rest.VerificationReportDto;
import main.dto.rest.VerificationReportFullDto;
import main.service.interfaces.IVerificationReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
@AllArgsConstructor
@RestController
@RequestMapping("reports/verifications")
public class VerificationReportController {
    @Autowired
    private IVerificationReportService verificationReportService;


    @PostMapping
    public ResponseEntity<?> saveReport(@RequestBody VerificationReportFullDto reportDto){
        return verificationReportService.save(reportDto);
    }

    @GetMapping("/pages")
    public Page<VerificationReportDto> getAllWithPagination(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNum,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "dir", defaultValue = AppConstants.DEFAULT_PAGE_SORT_DIR, required = false) String pageDir){
        Pageable pageable = PageRequest.of(pageNum, pageSize,
                Sort.by(Sort.Direction.valueOf(pageDir.toUpperCase()), "creationDate"));
        return verificationReportService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVerificationReport(@PathVariable("id") String id){
        return verificationReportService.findById(Long.parseLong(id));
    }

    @GetMapping("/update/{id}")
    public ResponseEntity<?> updateReportFromArshin(@PathVariable("id") String id){
        return verificationReportService.updateReportFromArshin(Long.parseLong(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVerificationReport(@PathVariable("id") String id){
        return verificationReportService.delete(Long.parseLong(id));
    }

    @PatchMapping()
    public ResponseEntity<?> updateVerificationReport(@RequestBody VerificationReportFullDto reportDto){
        return verificationReportService.update(reportDto);
    }
}
