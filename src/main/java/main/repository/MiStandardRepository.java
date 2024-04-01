package main.repository;

import main.model.MiStandard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MiStandardRepository extends JpaRepository<MiStandard, Long> {
    MiStandard findByArshinNumber(String arshinNumber);
    Page<MiStandard> findByArshinNumberContainingOrSchemaTitleContaining(String arshinNumber, String schemaTitle, Pageable pageable);
}
