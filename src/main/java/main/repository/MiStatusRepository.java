package main.repository;

import main.model.MiStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MiStatusRepository extends JpaRepository<MiStatus, Long> {
    MiStatus findByStatus(String status);
    Page<MiStatus> findByStatusIgnoreCaseContaining(String status, Pageable pageable);
    List<MiStatus> findByStatusIgnoreCaseContaining(String status);
}
