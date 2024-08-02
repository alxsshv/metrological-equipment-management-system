package main.repository;

import main.model.MiCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MiConditionRepository extends JpaRepository<MiCondition, Long> {
    MiCondition findByTitle(String title);
    Page<MiCondition> findByTitleIgnoreCaseContaining(String title, Pageable pageable);
    List<MiCondition> findByTitleIgnoreCaseContaining(String title);
}
