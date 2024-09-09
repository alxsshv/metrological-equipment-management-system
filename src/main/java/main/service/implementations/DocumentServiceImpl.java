package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.Setter;
import main.config.AppUploadPaths;
import main.dto.rest.DocumentDto;
import main.dto.rest.mappers.DocumentDtoMapper;
import main.model.Document;
import main.repository.DocumentRepository;
import main.service.Category;
import main.service.ServiceMessage;
import main.service.interfaces.DocumentService;
import main.service.utils.FileContentTypeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class DocumentServiceImpl implements DocumentService {
    private final static Logger log = LoggerFactory.getLogger(DocumentServiceImpl.class);
    @Autowired
    private AppUploadPaths appUploadPaths;
    @Autowired
    private final DocumentRepository documentRepository;
    public DocumentServiceImpl(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }


    @Override
    public void uploadAll(MultipartFile[] files, String[] descriptions, Category category, Long categoryId) throws IOException {
        for (int i = 0; i < files.length; i++) {
            addDocument(files[i], descriptions[i], category, categoryId);
        }
    }

    private void createFolderIfNotExist(){
        File uploadFolder = new File(appUploadPaths.getDocumentsPath());
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
                file.transferTo(new File(appUploadPaths.getDocumentsPath() + "/" + storageFileName));
                documentRepository.save(document);
                log.info("Файл {} успешно загружен на сервер", filename );
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
            log.error("{}:{}", errorMessage, ex.getMessage());
            return ResponseEntity.status(500).body(new ServiceMessage(errorMessage));
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
                .body(new ByteArrayResource(Files.readAllBytes(Path.of(appUploadPaths.getDocumentsPath() + document.getStorageFileName()))));
    }

    @Override
    public void delete(long id) throws IOException {
            Document document = getDocumentById(id);
            Files.deleteIfExists(Path.of(appUploadPaths.getDocumentsPath() + "/" + document.getStorageFileName()));
            documentRepository.delete(document);
    }

    @Override
    public void deleteAll(Category category, long categoryId) throws IOException {
        List<Document> documents = documentRepository.findByCategoryNameAndCategoryId(category.name(), categoryId);
        for (Document document : documents){
            delete(document.getId());
        }
    }

}





