package main.dto.rest.mappers;

import main.dto.rest.DocumentDto;
import main.model.Document;

import static main.dto.rest.mappers.utils.DateStringConverter.getISOStringOrNull;

public class DocumentDtoMapper {
    public static DocumentDto mapToDto(Document document){
        DocumentDto dto = new DocumentDto();
        dto.setId(document.getId());
        dto.setDescription(document.getDescription());
        dto.setExtension(document.getExtension());
        dto.setStorageFileName(document.getStorageFileName());
        dto.setOriginalFilename(document.getOriginalFilename());
        dto.setCategoryName(document.getCategoryName());
        dto.setCategoryId(document.getCategoryId());
        dto.setUploadingDate(getISOStringOrNull(document.getUploadingDate()));
        dto.setUpdatingDate(getISOStringOrNull(document.getUpdatingDate()));
        return dto;
    }

    public static Document mapToEntity(DocumentDto dto){
        Document document = new Document();
        document.setId(dto.getId());
        document.setOriginalFilename(dto.getOriginalFilename());
        document.setDescription(dto.getDescription());
        document.setExtension(dto.getExtension());
        document.setStorageFileName(dto.getStorageFileName());
        document.setCategoryName(dto.getCategoryName());
        document.setCategoryId(dto.getCategoryId());
        return document;
    }
}
