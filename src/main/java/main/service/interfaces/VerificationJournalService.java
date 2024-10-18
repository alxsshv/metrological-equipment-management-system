package main.service.interfaces;

import jakarta.validation.Valid;
import main.dto.rest.VerificationJournalDto;
import main.model.VerificationJournal;
import main.service.validators.VerificationJournalAlreadyExist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VerificationJournalService {
    void save(@VerificationJournalAlreadyExist @Valid VerificationJournalDto journalDto);
    VerificationJournalDto findById(long id);
    VerificationJournal getById(long id);
    List<VerificationJournalDto> findBySearchString(String searchString);
    Page<VerificationJournalDto> findBySearchString(String searchString, Pageable pageable);
    List<VerificationJournalDto> findAll();
    Page<VerificationJournalDto> findAll(Pageable pageable);
    void update(@Valid VerificationJournalDto journalDto);
    void delete(long id);
}
