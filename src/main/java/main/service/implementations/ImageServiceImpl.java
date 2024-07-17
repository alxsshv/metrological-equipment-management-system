package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import main.dto.rest.ImageDto;
import main.dto.rest.mappers.ImageDtoMapper;
import main.model.Image;
import main.repository.ImageRepository;
import main.service.Category;
import main.service.interfaces.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
public class ImageServiceImpl implements ImageService {
    private final static Logger log = LoggerFactory.getLogger(ImageServiceImpl.class);
    private String imageUploadPath;
    private final ImageRepository imageRepository;


    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Value("${upload.images.path}")
    public void setImageUploadPath(String imageUploadPath) {
        this.imageUploadPath = imageUploadPath;
    }

    @Override
    public void uploadAll(MultipartFile[] files, String[] descriptions, Category category, Long categoryId) throws IOException {
        for (int i = 0; i < files.length; i++) {
            addImage(files[i], descriptions[i], category, categoryId);
        }
    }

    private void createFolderIfNotExist(){
        File uploadFolder = new File(imageUploadPath);
        if (!uploadFolder.exists()){
            uploadFolder.mkdir();
        }
    }

    @Override
    public void delete(long id) throws IOException {
        try {
            Image image = getImageById(id);
            Files.deleteIfExists(Path.of(imageUploadPath + "/" + image.getStorageFileName()));
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
                file.transferTo(new File(imageUploadPath + "/" + storageFileName));
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
