package main.controller;

import lombok.AllArgsConstructor;
import main.dto.DocumentDto;
import main.dto.ImageDto;
import main.service.Category;
import main.service.implementations.DocumentService;
import main.service.implementations.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("files")
public class FileController {
    @Autowired
    private  final DocumentService documentService;
    @Autowired
    private final ImageService imageService;

    @GetMapping("/documents")
    public List<DocumentDto> getDocumentsList(@RequestParam (value ="category") String categoryName,
                                              @RequestParam(value = "id") String id){
    return documentService.getDocuments(Category.valueOf(categoryName),Long.parseLong(id));
    }

    @GetMapping("/documents/{id}")
    public ResponseEntity<?> getDocumentFile(@PathVariable(value = "id") String id) throws IOException {
        return documentService.getDocumentFile(Long.parseLong(id));
    }

    @GetMapping("/images")
    public List<ImageDto> getImageList(@RequestParam (value ="category") String categoryName,
                                       @RequestParam(value = "id") String id){
        return imageService.getImages(Category.valueOf(categoryName),Long.parseLong(id));
    }




}
