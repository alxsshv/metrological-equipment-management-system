package main.dto.mappers;

import main.dto.DocumentDto;
import main.model.Document;

public class DocumentDtoMapper {
    public static DocumentDto mapToDto(Document document){
        DocumentDto dto = new DocumentDto();
        dto.setId(document.getId());
        dto.setDescription(document.getDescription());
        dto.setExtension(document.getExtension());
        dto.setStorageFileName(document.getStorageFileName());
        dto.setCategoryName(document.getCategoryName());
        dto.setCategoryId(document.getCategoryId());
        dto.setUploadingDate(document.getUploadingDate());
        dto.setUpdatingDate(document.getUpdatingDate());
        return dto;
    }

    public static Document mapToEntity(DocumentDto dto){
        Document document = new Document();
        document.setId(dto.getId());
        document.setDescription(dto.getDescription());
        document.setExtension(dto.getExtension());
        document.setStorageFileName(dto.getStorageFileName());
        document.setCategoryName(dto.getCategoryName());
        document.setCategoryId(dto.getCategoryId());
        return document;
    }
}
