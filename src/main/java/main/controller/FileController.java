package main.controller;

import lombok.AllArgsConstructor;
import main.dto.DocumentDto;
import main.service.Category;
import main.service.implementations.DocumentService;
import main.service.implementations.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("files")
public class FileController {
    @Autowired
    private  final DocumentService documentService;

    @GetMapping("/documents/")
    public List<DocumentDto> getDocumentsList(@RequestParam (value ="category") String categoryName,
                                              @RequestParam(value = "id") String id){
    return documentService.getDocuments(Category.valueOf(categoryName),Long.parseLong(id));
    }


}
