package main.service.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import main.dto.rest.VerificationJournalDto;
import main.model.VerificationJournal;
import main.repository.VerificationJournalRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@AllArgsConstructor
public class VerificationJournalAlreadyExistValidator implements ConstraintValidator<VerificationJournalAlreadyExist, VerificationJournalDto> {

    @Autowired
    private VerificationJournalRepository journalRepository;


    @Override
    public boolean isValid(VerificationJournalDto journalDto, ConstraintValidatorContext constraintValidatorContext) {
        Optional<VerificationJournal> journalOpt = journalRepository.findByNumber(journalDto.getNumber());
        return journalOpt.isEmpty();
    }
}
