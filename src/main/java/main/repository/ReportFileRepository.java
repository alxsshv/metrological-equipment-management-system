package main.repository;

import main.model.ReportFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReportFileRepository extends JpaRepository<ReportFile, Long> {

    Optional<ReportFile> findByStorageFileName(String storageFileName);
    List<ReportFile> findByCategoryNameAndCategoryId(String categoryName, long categoryId);
    Page<ReportFile> findByCategoryName(String categoryName, Pageable pageable);
}
