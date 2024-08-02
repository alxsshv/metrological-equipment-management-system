package main.repository;

import main.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByCategoryNameAndCategoryId(String categoryName, long categoryId);
}
