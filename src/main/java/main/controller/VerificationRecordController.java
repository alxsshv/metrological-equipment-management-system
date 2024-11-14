package main.controller;

import lombok.extern.slf4j.Slf4j;
import main.config.AppConstants;
import main.dto.rest.VerificationRecordDto;
import main.service.ServiceMessage;
import main.service.implementations.VerificationRecordServiceImpl;
import main.service.interfaces.VerificationRecordService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/verifications/records")
@Slf4j
public class VerificationRecordController {
    private final VerificationRecordService recordService;

    public VerificationRecordController(VerificationRecordServiceImpl recordService) {
        this.recordService = recordService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVerificationRecord(@PathVariable ("id") long id){
        VerificationRecordDto recordDto = recordService.findById(id);
        return ResponseEntity.ok(recordDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVerificationRecord(@RequestBody VerificationRecordDto recordDto){
        recordService.update(recordDto);
        String okMessage = "Запись о поверке № " + recordDto.getId() + " успешно обновлена";
        log.info(okMessage);
        return ResponseEntity.ok().body(new ServiceMessage(okMessage));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVerificationRecord(@PathVariable ("id") long id){
        recordService.delete(id);
        String okMessage = "Запись о поверке № " + id + " удалена";
        log.info(okMessage);
        return ResponseEntity.ok().body(new ServiceMessage(okMessage));
    }


    @GetMapping("/counters/dates/{employeeId}/pages")
    public ResponseEntity<?> getVerificationsAmountByEmployeeWithPagination(
            @PathVariable ("employeeId") String employeeId,
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNum,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "dir", defaultValue = AppConstants.DEFAULT_PAGE_SORT_DIR, required = false) String pageDir){
        Pageable pageable = PageRequest.of(pageNum, pageSize,
                Sort.by(Sort.Direction.valueOf(pageDir.toUpperCase()), "verification_date"));
        Page<Map<String,Integer>> counters = recordService.findVerificationAmountForEveryDateByEmployeeId(Long.parseLong(employeeId),pageable);
        return ResponseEntity.ok(counters);
    }

}
