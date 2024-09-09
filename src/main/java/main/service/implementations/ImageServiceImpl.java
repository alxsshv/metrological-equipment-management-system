package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import main.config.AppUploadPaths;
import main.dto.rest.ImageDto;
import main.dto.rest.mappers.ImageDtoMapper;
import main.model.Image;
import main.repository.ImageRepository;
import main.service.Category;
import main.service.ServiceMessage;
import main.service.interfaces.ImageService;
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
@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final static Logger log = LoggerFactory.getLogger(ImageServiceImpl.class);
    @Autowired
    private final ImageRepository imageRepository;
    @Autowired
    private AppUploadPaths appUploadPaths;

    @Override
    public void uploadAll(MultipartFile[] files, String[] descriptions, Category category, Long categoryId) throws IOException {
        for (int i = 0; i < files.length; i++) {
            addImage(files[i], descriptions[i], category, categoryId);
        }
    }

    private void createFolderIfNotExist(){
        File uploadFolder = new File(appUploadPaths.getImagesPath());
        if (!uploadFolder.exists()){
            uploadFolder.mkdir();
        }
    }

    @Override
    public void delete(long id) throws IOException {
        try {
            Image image = getImageById(id);
            Files.deleteIfExists(Path.of(appUploadPaths.getImagesPath() + "/" + image.getStorageFileName()));
            imageRepository.delete(image);
        } catch (IOException ex) {
            throw new RuntimeException("Ошибка удаления файла");
        }
    }

    @Override
    public Image getImageById(long id) {
        Optional<Image> imageOpt = imageRepository.findById(id);
        if (imageOpt.isEmpty()){
            throw new EntityNotFoundException("Изображение  № "+ id +" не найдено");
        }
        return imageOpt.get();
    }

    @Override
    public ResponseEntity<?> getImageFile(Long id) {
        try {
            Image image = getImageById(id);
            ResponseEntity<?> responseEntity = buildResponseEntityFrom(image);
            String okMessage = "Изображение " + image.getStorageFileName() + " передано";
            log.info(okMessage);
            return responseEntity;
        } catch (IOException ex) {
            String errorMessage = "Файл изображения не найден или поврежден. ";
            log.error("{}:{}", errorMessage, ex.getMessage());
            return ResponseEntity.status(500).body(new ServiceMessage(errorMessage));
        }
    }

    private ResponseEntity<?> buildResponseEntityFrom(Image image) throws IOException {
        ContentDisposition contentDisposition = ContentDisposition.builder("inline")
                .filename(image.getOriginalFileName(), StandardCharsets.UTF_8).build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(FileContentTypeBuilder.getContentType(image.getExtension()));
        headers.setContentDisposition(contentDisposition);
        return ResponseEntity.ok()
                .headers(headers)
                .body(new ByteArrayResource(Files.readAllBytes(Path.of(appUploadPaths.getImagesPath() + image.getStorageFileName()))));
    }

    @Override
    public void deleteAll(Category category, long categoryId) throws IOException {
        List<Image> images = imageRepository.findByCategoryNameAndCategoryId(category.name(), categoryId);
        for (Image image : images){
            delete(image.getId());
        }
    }

    @Override
    public void addImage(MultipartFile file, String description, Category category, Long CategoryId) throws IOException {
        if (file != null){
                createFolderIfNotExist();
                String filename = file.getOriginalFilename();
                String extension =  filename.substring(filename.lastIndexOf(".")+1);
                String storageFileName = UUID.randomUUID() + "." + filename;
                Image image = new Image();
                image.setStorageFileName(storageFileName);
                if (description.isEmpty()){
                description = file.getOriginalFilename();
                }
                image.setDescription(description);
                image.setOriginalFileName(file.getOriginalFilename());
                image.setExtension(extension);
                image.setCategoryName(category.name());
                image.setCategoryId(CategoryId);
                file.transferTo(new File(appUploadPaths.getImagesPath() + "/" + storageFileName));
                imageRepository.save(image);
            log.info("Файл {} успешно загружен на сервер", filename);
        }
    }

    @Override
    public List<ImageDto> getImages(Category category, long categoryId){
        List<Image> images = imageRepository.findByCategoryNameAndCategoryId(category.name(), categoryId);
        return images.stream().map(ImageDtoMapper::mapToDto).toList();
    }

}
