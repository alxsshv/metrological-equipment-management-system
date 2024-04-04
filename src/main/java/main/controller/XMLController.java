package main.controller;

import main.service.xml.IXMLService;
import main.service.xml.XMLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;


@Controller
public class XMLController {
    @Autowired
    private XMLService xmlService;

    @GetMapping(value = "/xml/{id}", produces = "application/xml")
    @ResponseBody
    public ResponseEntity<?> createXMLReportForFsa(@RequestParam("id") String id) throws IOException {
        return xmlService.getFsaXMLForIssue(id);
    }
}
