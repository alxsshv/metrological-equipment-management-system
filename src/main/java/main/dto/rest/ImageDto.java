package main.dto.rest;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ImageDto {
    private long id;
    private String storageFileName;
    private String description;
    private String extension;
    private String categoryName;
    private long categoryId;
    private String uploadingDate;
}
