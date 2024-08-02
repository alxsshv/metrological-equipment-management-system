package main.repository;

import main.model.MeasCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasCategoryRepository extends JpaRepository<MeasCategory, Long> {
    MeasCategory findByTitle(String title);
    Page<MeasCategory> findByTitleIgnoreCaseContaining(String title, Pageable pageable);
    List<MeasCategory> findByTitleIgnoreCaseContaining(String title);
}
