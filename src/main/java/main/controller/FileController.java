package main.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.dto.rest.DocumentDto;
import main.dto.rest.ImageDto;
import main.service.Category;
import main.service.ServiceMessage;
import main.service.interfaces.DocumentService;
import main.service.interfaces.FileService;
import main.service.interfaces.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("files")
public class FileController {
    @Autowired
    private  final DocumentService documentService;
    @Autowired
    private final ImageService imageService;
    @Autowired
    private final FileService fileService;

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
    public ResponseEntity<?> deleteDocument(@PathVariable(value = "id") String id) throws IOException {
        documentService.delete(Long.parseLong(id));
        String okMessage = "Файл успешно удален";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }

    @DeleteMapping("/images/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable(value = "id") String id) throws IOException {
        imageService.delete(Long.parseLong(id));
        String okMessage = "Файл успешно удален";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }

    @GetMapping("/documents/{id}")
    public ResponseEntity<?> getDocumentFile(@PathVariable(value = "id") long id) {
        return  documentService.getDocumentFile(id);
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<?> getImageFile(@PathVariable(value = "id") long id) {
        return  imageService.getImageFile(id);
    }

    @GetMapping("/images")
    public List<ImageDto> getImageList(@RequestParam (value ="category") String categoryName,
                                       @RequestParam(value = "id") String id){
        return imageService.getImages(Category.valueOf(categoryName),Long.parseLong(id));
    }




}
