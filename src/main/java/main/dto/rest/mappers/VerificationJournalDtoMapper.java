package main.dto.rest.mappers;


import main.dto.rest.VerificationJournalDto;
import main.model.VerificationJournal;

public class VerificationJournalDtoMapper {
    public static VerificationJournal map(VerificationJournalDto dto){
        VerificationJournal journal = new VerificationJournal();
        journal.setNumber(dto.getNumber());
        journal.setTitle(dto.getTitle());
        journal.setDescription(dto.getDescription());
        return journal;
    }

    public static VerificationJournalDto map(VerificationJournal journal){
        VerificationJournalDto dto = new VerificationJournalDto();
        dto.setId(journal.getId());
        dto.setNumber(journal.getNumber());
        dto.setDescription(journal.getDescription());
        dto.setTitle(journal.getTitle());
        return dto;
    }
}
