package main.service.implementations;

import main.model.Document;
import main.repository.DocumentRepository;
import main.service.Category;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class DocumentService {
    @Value("${upload.documents.path}")
    private String documentUploadPath;
    private DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public void uploadAll(MultipartFile[] files, String[] descriptions, Category category, Long CategoryId) throws IOException {
        for (int i = 0; i < files.length; i++) {
            if (files[i] != null){
                File uploadFolder = new File(documentUploadPath);
                if (!uploadFolder.exists()){
                    uploadFolder.mkdir();
                }
                String filename = files[i].getOriginalFilename();
                String extension =  filename.substring(filename.lastIndexOf(".")+1);
                String storageFileName = UUID.randomUUID() + "." + filename;
                files[i].transferTo(new File(documentUploadPath + "/" + storageFileName));
                Document document = new Document();
                document.setStorageFileName(storageFileName);
                document.setDescription(descriptions[i]);
                document.setExtension(extension);
                document.setCategory(category);
                document.setCategoryId(CategoryId);
                documentRepository.save(document);
            }

        }
    }
}
