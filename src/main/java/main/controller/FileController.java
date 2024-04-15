package main.controller;

import lombok.AllArgsConstructor;
import main.dto.DocumentDto;
import main.service.Category;
import main.service.implementations.DocumentService;
import main.service.implementations.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("files")
public class FileController {
    @Autowired
    private  final DocumentService documentService;

    @GetMapping("/documents")
    public List<DocumentDto> getDocumentsList(@RequestParam (value ="category") String categoryName,
                                              @RequestParam(value = "id") String id){
    return documentService.getDocuments(Category.valueOf(categoryName),Long.parseLong(id));
    }

    @GetMapping("/documents/{id}")
    public ResponseEntity<?> getDocumentFile(@PathVariable(value = "id") String id) throws IOException, URISyntaxException {
        return documentService.getDocumentFile(Long.parseLong(id));
    }


}
