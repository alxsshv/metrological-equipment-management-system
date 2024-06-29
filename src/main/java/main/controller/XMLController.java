package main.controller;


import lombok.AllArgsConstructor;
import main.service.interfaces.XMLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
@RequestMapping("/xml")
public class XMLController {
    @Autowired
    private XMLService xmlService;

    @GetMapping("/arshin/{id}")
    public ResponseEntity<?> getArshinReportFile(@PathVariable ("id") long id) {
        return xmlService.getXMLFileForArshin(id);
    }

    @GetMapping("/fsa/{id}")
    public ResponseEntity<?> getFsaReportFile(@PathVariable ("id") long id)  {
        return xmlService.getXMLFileForFSA(id);
    }
}
