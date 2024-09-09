package main.dto.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReportFileDto {
    private long id;
    private String originalFilename;
    private String storageFileName;
    private String description;
    private String extension;
    private String categoryName;
    private long categoryId;
    private String creationDate;
}
