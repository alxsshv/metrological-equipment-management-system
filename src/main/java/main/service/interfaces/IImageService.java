package main.service.interfaces;

import main.dto.ImageDto;
import main.service.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IImageService {
    void uploadAll(MultipartFile[] files, String[] descriptions, Category category, Long categoryId) throws IOException;
    public ResponseEntity<?> delete(long id) throws IOException;
    void deleteAll(Category category, long categoryId) throws IOException;
    void addImage(MultipartFile file, String description, Category category, Long CategoryId) throws IOException;
    List<ImageDto> getImages(Category category, long categoryId);

}
