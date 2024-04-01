package main.repository;

import main.model.VerificationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRecordRepository extends JpaRepository<VerificationRecord, Long> {
}
