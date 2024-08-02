package main.repository;

import main.model.VerificationRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Map;

public interface VerificationRecordRepository extends JpaRepository<VerificationRecord, Long> {

    @Query(value = "select verification_date, COUNT(*) verifications_count " +
            "from verification_records vr where employee_id = :employeeId" +
            " GROUP BY (vr.verification_date)", nativeQuery = true)
    Page<Map<String,Integer>> findVerificationAmountForEveryDateByEmployeeId(@Param("employeeId") long id, Pageable pageable);


}

