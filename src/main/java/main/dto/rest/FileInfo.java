package main.dto.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.service.Category;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo {
    private String number;
    private String description;
    private Category category;
    private String categoryId;
}
