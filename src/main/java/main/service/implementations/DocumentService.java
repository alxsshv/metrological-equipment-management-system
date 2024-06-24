package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.Setter;
import main.dto.rest.DocumentDto;
import main.dto.rest.mappers.DocumentDtoMapper;
import main.model.Document;
import main.repository.DocumentRepository;
import main.service.Category;
import main.service.ServiceMessage;
import main.service.interfaces.IDocumentService;
import main.service.utils.FileContentTypeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Getter
@Setter
@Service
public class DocumentService implements IDocumentService {
    private final static Logger log = LoggerFactory.getLogger(DocumentService.class);
    @Autowired
    private final DocumentRepository documentRepository;
    private  String documentUploadPath;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Value("${upload.documents.path}")
    public void setDocumentUploadPath(String documentUploadPath){
        this.documentUploadPath = documentUploadPath;
    }

    @Override
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

    @Override
    public void addDocument(MultipartFile file, String description, Category category, Long CategoryId) throws IOException {
        if (file != null){
                createFolderIfNotExist();
                String filename = file.getOriginalFilename();
                String extension =  filename.substring(filename.lastIndexOf(".")+1);
                String storageFileName = UUID.randomUUID() + "." + filename;
                Document document = new Document();
                document.setStorageFileName(storageFileName);
                document.setOriginalFilename(file.getOriginalFilename());
                if (description.isEmpty()){
                    description = file.getOriginalFilename();
                }
                document.setDescription(description);
                document.setExtension(extension);
                document.setCategoryName(category.name());
                document.setCategoryId(CategoryId);
                file.transferTo(new File(documentUploadPath + "/" + storageFileName));
                documentRepository.save(document);
                log.info("Файл " + filename + " успешно загружен на сервер");
        }

    }

    @Override
    public ResponseEntity<?> descriptionUpdate(long id, String description){
        try {
            Document document = getDocumentById(id);
            document.setDescription(description);
            documentRepository.save(document);
            String okMessage = "Описание файла " + id + " успешно обновлено";
            log.info(okMessage);
            return ResponseEntity.ok(new ServiceMessage(okMessage));
        } catch (EntityNotFoundException ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(404).body(new ServiceMessage(ex.getMessage()));
        }
    }

    @Override
    public List<DocumentDto> getDocuments(Category category, long categoryId){
        List<Document> documents = documentRepository.findByCategoryNameAndCategoryId(category.name(), categoryId);
        return documents.stream().map(DocumentDtoMapper ::mapToDto).toList();
    }

    @Override
    public ResponseEntity<?> getDocumentFile(Long id) {
        try {
            Document document = getDocumentById(id);
            ResponseEntity<?> responseEntity = buildResponseEntityFrom(document);
            String okMessage = "Файл " + document.getStorageFileName() + " передан";
            log.info(okMessage);
            return responseEntity;
        } catch (IOException ex) {
            String errorMessage = "Файл не найден или поврежден. ";
            log.error(errorMessage + ex.getMessage());
            return ResponseEntity.status(500).body(new ServiceMessage(errorMessage));
        } catch (EntityNotFoundException ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(400).body(new ServiceMessage(ex.getMessage()));
        }
    }

    @Override
    public Document getDocumentById(long id){
        Optional<Document> documentOpt = documentRepository.findById(id);
        if(documentOpt.isEmpty()){
            throw new EntityNotFoundException("Документ № "+ id +" не найден");
        }
        return documentOpt.get();
    }

    private ResponseEntity<?> buildResponseEntityFrom(Document document) throws IOException {
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename(document.getOriginalFilename(), StandardCharsets.UTF_8).build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(FileContentTypeBuilder.getContentType(document.getExtension()));
        headers.setContentDisposition(contentDisposition);
        return ResponseEntity.ok()
                .headers(headers)
                .body(new ByteArrayResource(Files.readAllBytes(Path.of(documentUploadPath + document.getStorageFileName()))));
    }

    @Override
    public ResponseEntity<?> delete(long id) throws IOException {
        try {
            Document document = getDocumentById(id);
            Files.deleteIfExists(Path.of(documentUploadPath + "/" + document.getStorageFileName()));
            documentRepository.delete(document);
            String okMessage = "Файл " + document.getStorageFileName() + " успешно удален";
            log.info(okMessage);
            return ResponseEntity.ok(new ServiceMessage(okMessage));
        } catch (EntityNotFoundException ex){
            log.info(ex.getMessage());
            return ResponseEntity.status(404).body(new ServiceMessage(ex.getMessage()));
        }
    }

    @Override
    public void deleteAll(Category category, long categoryId) throws IOException {
        List<Document> documents = documentRepository.findByCategoryNameAndCategoryId(category.name(), categoryId);
        for (Document document : documents){
            delete(document.getId());
        }
    }

}





