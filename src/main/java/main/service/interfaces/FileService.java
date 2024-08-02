package main.service.interfaces;

import main.service.Category;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    void uploadAllFiles (MultipartFile[] files, String[] descriptions, Category category, Long categoryId) throws IOException;
    void uploadFile(MultipartFile file, String description, Category category, Long categoryId) throws IOException;
    void deleteAllFiles(Category category, long categoryId) throws IOException;
}
