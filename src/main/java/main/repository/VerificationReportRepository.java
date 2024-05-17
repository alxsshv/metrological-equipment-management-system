package main.repository;

import main.model.VerificationReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationReportRepository extends JpaRepository<VerificationReport, Long> {
    @Override
    Page<VerificationReport> findAll(Pageable pageable);
}
