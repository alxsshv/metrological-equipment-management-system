package main.repository;

import main.model.VerificationRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationRecordRepository extends JpaRepository<VerificationRecord, Long> {
}
