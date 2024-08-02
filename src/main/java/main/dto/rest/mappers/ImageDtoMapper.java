package main.dto.rest.mappers;


import main.dto.rest.ImageDto;
import main.dto.rest.mappers.utils.DateStringConverter;
import main.model.Image;

public class ImageDtoMapper {
    public static ImageDto mapToDto(Image image){
        ImageDto dto = new ImageDto();
        dto.setId(image.getId());
        dto.setExtension(image.getExtension());
        dto.setDescription(image.getDescription());
        dto.setStorageFileName(image.getStorageFileName());
        dto.setCategoryName(image.getCategoryName());
        dto.setCategoryId(image.getCategoryId());
        dto.setUploadingDate(DateStringConverter.getISOStringOrNull(image.getUploadingDate()));
        return dto;
    }

    public static Image mapToEntity(ImageDto dto){
        Image image = new Image();
        image.setId(dto.getId());
        image.setExtension(dto.getExtension());
        image.setDescription(dto.getDescription());
        image.setStorageFileName(dto.getStorageFileName());
        image.setCategoryName(dto.getCategoryName());
        image.setCategoryId(dto.getCategoryId());
        return image;
    }
}
