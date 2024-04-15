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
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;
import static org.springframework.http.MediaType.APPLICATION_PDF_VALUE;

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

    public ResponseEntity<?> getDocumentFile(Long id) throws IOException, URISyntaxException {
        Optional<Document> documentOpt = documentRepository.findById(id);
        if(documentOpt.isEmpty()){
            String errorMessage = "Документ № "+ id +" не найден";
            log.info(errorMessage);
            return ResponseEntity.status(404).body(
                    new ServiceMessage(errorMessage));
        }
        Document document = documentOpt.get();
        File documentFile = new File(documentUploadPath + document.getStorageFileName());
        if (!documentFile.exists()){
        String errorMessage = "Файл " + document.getStorageFileName() + " не найден";
            log.info(errorMessage);
            return ResponseEntity.status(404).body(
                    new ServiceMessage(errorMessage));
        }
        String okMessage = "Файл " + document.getStorageFileName() + " передан";
        log.info(okMessage);
        return ResponseEntity.ok()
                .header("Content-Type", getContentTypeString(document))
                .header("Content-Disposition", "attachment; filename=\""+ document.getOriginalFilename()+"\"")
                .body(new ByteArrayResource(Files.readAllBytes(Path.of(documentUploadPath + document.getStorageFileName()))));
    }
    private String getContentTypeString (Document document){
        switch(document.getExtension().toLowerCase()){
            case "pdf" : return APPLICATION_PDF_VALUE;
            case "doc", "dot": return "application/msword";
            case "docx" : return "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
            case "dotx" : return "application/vnd.openxmlformats-officedocument.wordprocessingml.template";
            case "docm", "dotm" : return "application/vnd.ms-word.document.macroEnabled.12";
            case "xls", "xlt", "xla" : return "application/vnd.ms-excel";
            case "xlsx" : return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
            case "xltx" : return "application/vnd.openxmlformats-officedocument.spreadsheetml.template";
            case "xlsm" : return "application/vnd.ms-excel.sheet.macroEnabled.12";
            case "xltm" : return "application/vnd.ms-excel.template.macroEnabled.12";
            case "xlam" : return "application/vnd.ms-excel.addin.macroEnabled.12";
            case "xlsb" : return "application/vnd.ms-excel.sheet.binary.macroEnabled.12";
            case "ppt", "pot", "pps", "ppa" : return "application/vnd.ms-powerpoint";
            case "pptx" : return "application/vnd.openxmlformats-officedocument.presentationml.presentation";
            case "potx" : return "application/vnd.openxmlformats-officedocument.presentationml.template";
            case "ppsx" : return "application/vnd.openxmlformats-officedocument.presentationml.slideshow";
            case "ppam" : return "application/vnd.ms-powerpoint.addin.macroEnabled.12";
            case "pptm" : return "application/vnd.ms-powerpoint.presentation.macroEnabled.12";
            case "potm" : return "application/vnd.ms-powerpoint.template.macroEnabled.12";
            case "ppsm" : return " application/vnd.ms-powerpoint.slideshow.macroEnabled.12";
            case "vsd" : return "application/vnd.visio";
            case "csv" : return "text/csv";
            case "ods":  return "application/vnd.oasis.opendocument.spreadsheet";
            case "odt": return "application/vnd.oasis.opendocument.text";
            case "odg" : return "application/vnd.oasis.opendocument.graphics";
            default: return APPLICATION_OCTET_STREAM_VALUE;
        }
    }




}
