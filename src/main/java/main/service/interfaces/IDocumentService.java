package main.service.interfaces;

import main.dto.rest.DocumentDto;
import main.model.Document;
import main.service.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IDocumentService {
    void uploadAll(MultipartFile[] files, String[] descriptions, Category category, Long categoryId) throws IOException;
    void addDocument(MultipartFile file, String description, Category category, Long CategoryId) throws IOException;
    List<DocumentDto> getDocuments(Category category, long categoryId);
    Document getDocumentById(long id);
    ResponseEntity<?> getDocumentFile(Long id);
    ResponseEntity<?> descriptionUpdate(long id, String description);
    ResponseEntity<?> delete(long id) throws IOException;
    void deleteAll(Category category, long categoryId) throws IOException;

}
