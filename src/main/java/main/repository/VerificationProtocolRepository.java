package main.repository;

import main.model.MeasurementInstrument;
import main.model.VerificationJournal;
import main.model.VerificationProtocol;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VerificationProtocolRepository extends JpaRepository<VerificationProtocol, Long> {
    Page<VerificationProtocol> findByJournal(VerificationJournal journal, Pageable pageable);
    List<VerificationProtocol> findByJournal(VerificationJournal journal);
    Page<VerificationProtocol> findByInstrument(MeasurementInstrument instrument, Pageable pageable);
    Page<VerificationProtocol> findByJournalAndNumberIgnoreCaseContainingOrDescriptionIgnoreCaseContaining(VerificationJournal journal,
                            String number, String description, Pageable pageable);
    Page<VerificationProtocol> findByInstrumentAndNumberIgnoreCaseContainingOrDescriptionIgnoreCaseContaining(MeasurementInstrument instrument,
                                                                                                              String number, String description, Pageable pageable);
}
