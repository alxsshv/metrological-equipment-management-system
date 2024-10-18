package main.controller;

import lombok.extern.slf4j.Slf4j;
import main.config.AppConstants;
import main.dto.rest.VerificationJournalDto;
import main.service.ServiceMessage;
import main.service.interfaces.VerificationJournalService;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/journals/verifications")
public class VerificationJournalController {
    @Autowired
    private VerificationJournalService journalService;

    @GetMapping("/pages")
    public Page<VerificationJournalDto> getVerificationJournalPageableList(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNum,
                                                                           @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                                           @RequestParam(value = "dir", defaultValue = AppConstants.DEFAULT_PAGE_SORT_DIR, required = false) String pageDir,
                                                                           @RequestParam(value = "search", defaultValue = "") String searchString) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.valueOf(pageDir.toUpperCase()), "number"));
        if (searchString.isEmpty() || searchString.equals("undefined")){
            return journalService.findAll(pageable);
        } else {
            return journalService.findBySearchString(searchString, pageable);
        }
    }

    @GetMapping("/search")
    public List<VerificationJournalDto> searchVerificationJournal(@RequestParam("Search") String searchString){
        return journalService.findBySearchString(searchString);
    }

    @GetMapping
    public List<VerificationJournalDto> getVerificationJournalList(){
        return journalService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVerificationJournalById(@PathVariable("id") long id){
        return ResponseEntity.ok(journalService.findById(id));
    }

    @PostMapping
    public ResponseEntity<?> addJournal(@RequestBody VerificationJournalDto verificationJournalDto){
        journalService.save(verificationJournalDto);
        String okMessage = "Создан новый журнал";
        log.info(okMessage);
        return ResponseEntity.status(201).body(new ServiceMessage(okMessage));
    }

    @PutMapping
    public ResponseEntity<?> editJournal(@RequestBody VerificationJournalDto verificationJournalDto){
        journalService.update(verificationJournalDto);
        String okMessage = "Данные журнала № "+ verificationJournalDto.getNumber() + " успешно обновлены";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }

    @DeleteMapping
    public ResponseEntity<?> editJournal(@PathVariable("id") long id){
        journalService.delete(id);
        String okMessage = "Журнал успешно удален";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }
}
