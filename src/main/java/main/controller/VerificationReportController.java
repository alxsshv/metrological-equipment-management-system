package main.controller;

import main.config.AppConstants;
import main.dto.VerificationReportDto;
import main.dto.VerificationReportFullDto;
import main.service.implementations.VerificationReportService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

@RestController
@RequestMapping("reports/verifications")
public class VerificationReportController {

    VerificationReportService verificationReportService;

    public VerificationReportController(VerificationReportService verificationReportService) {
        this.verificationReportService = verificationReportService;
    }

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
        return verificationReportService.getAllWithPagination(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVerificationReport(@PathVariable("id") String id){
        return verificationReportService.getById(Long.parseLong(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVerificationReport(@PathVariable("id") String id){
        return verificationReportService.deleteById(Long.parseLong(id));
    }
}
