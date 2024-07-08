package main.controller;

import main.config.AppConstants;
import main.dto.rest.VerificationRecordDto;
import main.service.implementations.VerificationRecordServiceImpl;
import main.service.interfaces.VerificationRecordService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/verifications/records")
public class VerificationRecordController {

    private final VerificationRecordService recordService;

    public VerificationRecordController(VerificationRecordServiceImpl recordService) {
        this.recordService = recordService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVerificationRecord(@PathVariable ("id") String id){
        return recordService.findById(Long.parseLong(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVerificationRecord(@RequestBody VerificationRecordDto recordDto){
        return recordService.update(recordDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVerificationRecord(@PathVariable ("id") String id){
        return recordService.delete(Long.parseLong(id));
    }


    @GetMapping("/counters/dates/{employeeId}/pages")
    public ResponseEntity<?> getVerificationsAmountByEmployeeWithPagination(
            @PathVariable ("employeeId") String employeeId,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNum,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "dir", defaultValue = AppConstants.DEFAULT_PAGE_SORT_DIR, required = false) String pageDir){
        Pageable pageable = PageRequest.of(pageNum, pageSize,
                Sort.by(Sort.Direction.valueOf(pageDir.toUpperCase()), "verification_date"));
        return recordService.findVerificationAmountForEveryDateByEmployeeId(Long.parseLong(employeeId),pageable);
    }

}
