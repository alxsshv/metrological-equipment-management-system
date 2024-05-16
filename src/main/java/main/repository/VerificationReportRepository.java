package main.repository;

import main.model.VerificationReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationReportRepository extends JpaRepository<VerificationReport, Long> {
}
