package main.repository;

import main.model.VerificationJournal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VerificationJournalRepository extends JpaRepository<VerificationJournal,Long> {

    Optional<VerificationJournal> findByNumber(String number);
    List<VerificationJournal> findByNumberIgnoreCaseContainingOrTitleIgnoreCaseContainingOrDescriptionIgnoreCaseContaining(String number, String title, String description);
    Page<VerificationJournal> findByNumberIgnoreCaseContainingOrTitleIgnoreCaseContainingOrDescriptionIgnoreCaseContaining(String number, String title, String description, Pageable pageable);
}
