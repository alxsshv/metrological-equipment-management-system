package main.dto.rest.mappers;

import main.dto.rest.ReportFileDto;
import main.model.ReportFile;

import static main.dto.rest.mappers.utils.DateStringConverter.getISOStringOrNull;

public class ReportFileDtoMapper {
    public static ReportFileDto mapToDto(ReportFile reportFile){
        ReportFileDto dto = new ReportFileDto();
        dto.setId(reportFile.getId());
        dto.setDescription(reportFile.getDescription());
        dto.setExtension(reportFile.getExtension());
        dto.setStorageFileName(reportFile.getStorageFileName());
        dto.setOriginalFilename(reportFile.getOriginalFileName());
        dto.setCategoryName(reportFile.getCategoryName());
        dto.setCategoryId(reportFile.getCategoryId());
        dto.setCreationDate(getISOStringOrNull(reportFile.getCreationDate()));
        return dto;
    }

    public static ReportFile mapToEntity(ReportFileDto dto){
        ReportFile reportFile = new ReportFile();
        reportFile.setId(dto.getId());
        reportFile.setOriginalFileName(dto.getOriginalFilename());
        reportFile.setDescription(dto.getDescription());
        reportFile.setExtension(dto.getExtension());
        reportFile.setStorageFileName(dto.getStorageFileName());
        reportFile.setCategoryName(dto.getCategoryName());
        reportFile.setCategoryId(dto.getCategoryId());
        return reportFile;
    }
}
