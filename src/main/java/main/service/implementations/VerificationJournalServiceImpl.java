package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import main.dto.rest.VerificationJournalDto;
import main.dto.rest.mappers.VerificationJournalDtoMapper;
import main.model.VerificationJournal;
import main.repository.VerificationJournalRepository;
import main.service.interfaces.VerificationJournalService;
import main.service.validators.VerificationJournalAlreadyExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Service
@Validated
public class VerificationJournalServiceImpl implements VerificationJournalService {
    @Autowired
    private VerificationJournalRepository journalRepository;

    @Override
    public void save(@VerificationJournalAlreadyExist @Valid VerificationJournalDto journalDto) {
        VerificationJournal journal = VerificationJournalDtoMapper.map(journalDto);
        journalRepository.save(journal);
    }

    @Override
    public VerificationJournalDto findById(long id) {
        VerificationJournal journal = getById(id);
        return VerificationJournalDtoMapper.map(journal);
    }

    @Override
    public VerificationJournal getById(long id) {
        Optional<VerificationJournal> journalOpt = journalRepository.findById(id);
        if (journalOpt.isEmpty()){
            throw new EntityNotFoundException("Журнал не найден");
        }
        return journalOpt.get();
    }

    @Override
    public List<VerificationJournalDto> findBySearchString(String searchString) {
        List<VerificationJournal> journals = journalRepository.
                findByNumberIgnoreCaseContainingOrTitleIgnoreCaseContainingOrDescriptionIgnoreCaseContaining(searchString, searchString, searchString);
        return journals.stream().map(VerificationJournalDtoMapper::map).toList();
    }

    @Override
    public Page<VerificationJournalDto> findBySearchString(String searchString, Pageable pageable) {
        Page<VerificationJournal> journals = journalRepository
                .findByNumberIgnoreCaseContainingOrTitleIgnoreCaseContainingOrDescriptionIgnoreCaseContaining(searchString,
                        searchString, searchString, pageable);
        return journals.map(VerificationJournalDtoMapper::map);
    }

    @Override
    public List<VerificationJournalDto> findAll() {
        return journalRepository.findAll().stream().map(VerificationJournalDtoMapper::map).toList();
    }

    @Override
    public Page<VerificationJournalDto> findAll(Pageable pageable) {
        return journalRepository.findAll(pageable).map(VerificationJournalDtoMapper::map);
    }

    @Override
    public void update(@Valid VerificationJournalDto journalDto) {
        VerificationJournal journal = getById(journalDto.getId());
        VerificationJournal updateJournalData = VerificationJournalDtoMapper.map(journalDto);
        journal.updateFrom(updateJournalData);
        journalRepository.save(journal);
    }

    @Override
    public void delete(long id) {
        journalRepository.deleteById(id);
    }
}
