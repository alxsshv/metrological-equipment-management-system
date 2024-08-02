package main.service.implementations;

import main.config.AppConstants;
import main.service.Category;
import main.service.interfaces.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
public class FileServiceImpl implements FileService {
    private final DocumentServiceImpl documentService;
    private final ImageServiceImpl imageService;

    public FileServiceImpl(DocumentServiceImpl documentService, ImageServiceImpl imageService) {
        this.documentService = documentService;
        this.imageService = imageService;
    }

    @Override
    public void uploadAllFiles (MultipartFile[] files, String[] descriptions, Category category, Long categoryId) throws IOException {
        String [] filesDescriptions = equalizeDescriptionsArrayLength(files, descriptions);
        for (int i = 0; i < files.length; i++) {
            if (files[i] != null){
                uploadFile(files[i], filesDescriptions[i], category, categoryId);
            }
        }
    }

    private String [] equalizeDescriptionsArrayLength(MultipartFile[] files, String[] descriptions){
        if (descriptions == null || descriptions.length == 0) {
            descriptions = new String[files.length];
            fillDescriptionsToFilesLength(descriptions,files,0);
        }
        if (files.length != descriptions.length){
            fillDescriptionsToFilesLength(descriptions, files,descriptions.length-1);
        }
        return descriptions;
    }

    private void fillDescriptionsToFilesLength(String[] descriptions, MultipartFile[] files, int startIndex){
        for (int i = startIndex; i < files.length; i++){
            descriptions[i] = "";
        }
    }

    @Override
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

    @Override
    public void deleteAllFiles(Category category, long categoryId) throws IOException {
        imageService.deleteAll(category,categoryId);
        documentService.deleteAll(category, categoryId);
    }
}
