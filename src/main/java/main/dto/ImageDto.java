package main.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

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
    private LocalDateTime uploadingDate;
}
