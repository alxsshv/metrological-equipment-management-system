package main.service.implementations;

import main.config.AppConstants;
import main.service.Category;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Arrays;

@Service
public class FileService {
    private final DocumentService documentService;
    private final ImageService imageService;

    public FileService(DocumentService documentService, ImageService imageService) {
        this.documentService = documentService;
        this.imageService = imageService;
    }

    public void uploadAllFiles (MultipartFile[] files, String[] descriptions, Category category, Long categoryId) throws IOException {
        String [] filesDescriptions = equalizeDescriptionsArrayLength(files, descriptions);
        for (int i = 0; i < files.length; i++) {
            if (files[i] != null){
                uploadFile(files[i], filesDescriptions[i], category, categoryId);
            }
        }
    }

    private String [] equalizeDescriptionsArrayLength(MultipartFile[] files, String[] descriptions){
        if (descriptions == null) {
            return new String[files.length];
        }
        if (files.length != descriptions.length){
            return Arrays.copyOf(descriptions, files.length);
        }
        return descriptions;
    }

    public void uploadFile(MultipartFile file, String description, Category category, Long categoryId) throws IOException {
        if (isImage(file)){
            imageService.addImage(file, description, category, categoryId);
            return;
        }
        documentService.addDocument(file, description, category, categoryId);
    }




    private boolean isImage(MultipartFile file){
        boolean result = false;
        for (String imageExtension : AppConstants.IMAGE_EXTENSIONS){
            result = result || getFileExtension(file).equalsIgnoreCase(imageExtension.toLowerCase());
        }
        return result;
    }

    private String getFileExtension(MultipartFile file){
        String filename = file.getOriginalFilename();
        return  filename.substring(filename.lastIndexOf(".")+1);
    }

    public void deleteAllFiles(Category category, long categoryId) throws IOException {
        imageService.deleteAll(category,categoryId);
        documentService.deleteAll(category, categoryId);
    }
}
