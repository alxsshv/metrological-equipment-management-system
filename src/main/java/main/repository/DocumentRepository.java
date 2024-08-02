package main.repository;

import main.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByCategoryNameAndCategoryId(String categoryName, long categoryId);
}
