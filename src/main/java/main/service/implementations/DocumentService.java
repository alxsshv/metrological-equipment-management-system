package main.service.implementations;

import main.dto.DocumentDto;
import main.dto.mappers.DocumentDtoMapper;
import main.model.Document;
import main.repository.DocumentRepository;
import main.service.Category;
import main.service.ServiceMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DocumentService {
    private final static Logger log = LoggerFactory.getLogger(DocumentService.class);
    @Value("${upload.documents.path}")
    private String documentUploadPath;
    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public void uploadAll(MultipartFile[] files, String[] descriptions, Category category, Long categoryId) throws IOException {
        for (int i = 0; i < files.length; i++) {
            addDocument(files[i], descriptions[i], category, categoryId);
        }
    }
    private void createFolderIfNotExist(){
        File uploadFolder = new File(documentUploadPath);
        if (!uploadFolder.exists()){
            uploadFolder.mkdir();
        }
    }

    public ResponseEntity<?> delete(long id) throws IOException {
        Optional<Document> documentOpt = documentRepository.findById(id);
        if (documentOpt.isEmpty()){
            String errorMessage = "Удаляемый файл не найден";
            log.info(errorMessage);
            return ResponseEntity.status(404).body(new ServiceMessage(errorMessage));
        }
        Document document = documentOpt.get();
        Files.deleteIfExists(Path.of(documentUploadPath + "/" + document.getStorageFileName()));
        documentRepository.delete(documentOpt.get());
        String okMessage ="Файл " + document.getStorageFileName() + " успешно удален";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }

    public void deleteAll(Category category, long categoryId) throws IOException {
        List<Document> documents = documentRepository.findByCategoryNameAndCategoryId(category.name(), categoryId);
        for (Document document : documents){
            delete(document.getId());
        }
    }

    public void addDocument(MultipartFile file, String description, Category category, Long CategoryId) throws IOException {
        if (file != null){
                createFolderIfNotExist();
                String filename = file.getOriginalFilename();
                String extension =  filename.substring(filename.lastIndexOf(".")+1);
                String storageFileName = UUID.randomUUID() + "." + filename;
                Document document = new Document();
                document.setStorageFileName(storageFileName);
                document.setDescription(description);
                document.setExtension(extension);
                document.setCategoryName(category.name());
                document.setCategoryId(CategoryId);
                file.transferTo(new File(documentUploadPath + "/" + storageFileName));
                documentRepository.save(document);
                log.info("Файл " + filename + " успешно загружен на сервер");
        }

    }
    public ResponseEntity<?> descriptionUpdate(long id, String description){
        Optional<Document> documentOpt = documentRepository.findById(id);
        if (documentOpt.isEmpty()){
            String errorMessage = "Файл с id " + id + " не найден";
            log.info(errorMessage);
            return ResponseEntity.status(404).body(new ServiceMessage(errorMessage));
        }
        Document document = documentOpt.get();
        document.setDescription(description);
        documentRepository.save(document);
        String okMessage = "Описание файла " + id + " успешно обновлено";
        log.info(okMessage);
        return  ResponseEntity.ok(new ServiceMessage(okMessage));
    }

    public List<DocumentDto> getDocuments(Category category, long categoryId){
        List<Document> documents = documentRepository.findByCategoryNameAndCategoryId(category.name(), categoryId);
        return documents.stream().map(DocumentDtoMapper ::mapToDto).toList();
    }

}
