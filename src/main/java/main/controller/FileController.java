package main.controller;

import lombok.AllArgsConstructor;
import main.dto.rest.DocumentDto;
import main.dto.rest.ImageDto;
import main.service.Category;
import main.service.interfaces.IDocumentService;
import main.service.interfaces.IFileService;
import main.service.interfaces.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("files")
public class FileController {
    @Autowired
    private  final IDocumentService documentService;
    @Autowired
    private final IImageService imageService;
    @Autowired
    private final IFileService fileService;

    @GetMapping("/documents")
    public List<DocumentDto> getDocumentsList(@RequestParam (value ="category") String categoryName,
                                              @RequestParam(value = "id") String id){
    return documentService.getDocuments(Category.valueOf(categoryName),Long.parseLong(id));
    }

    @PostMapping
    public void addFile(@RequestParam (value ="file") MultipartFile file,
                                     @RequestParam(value = "description") String description,
                                     @RequestParam(value = "category") String category,
                                     @RequestParam(value = "categoryId") String categoryId) throws IOException {
        fileService.uploadFile(file,description,Category.valueOf(category),Long.parseLong(categoryId));
    }


    @DeleteMapping("/documents/{id}")
    public ResponseEntity<?> getDeleteDocument(@PathVariable(value = "id") String id) throws IOException {
        return documentService.delete(Long.parseLong(id));
    }

    @GetMapping("/documents/{id}")
    public ResponseEntity<?> getDocumentFile(@PathVariable(value = "id") String id) {
        return documentService.getDocumentFile(Long.parseLong(id));
    }

    @GetMapping("/images")
    public List<ImageDto> getImageList(@RequestParam (value ="category") String categoryName,
                                       @RequestParam(value = "id") String id){
        return imageService.getImages(Category.valueOf(categoryName),Long.parseLong(id));
    }




}
