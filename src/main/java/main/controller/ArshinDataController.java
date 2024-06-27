package main.controller;

import main.service.interfaces.ArshinDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/arshin")
public class ArshinDataController {
    @Autowired
    private ArshinDataService arshinDataService;

    @GetMapping("/{number}")
    public ResponseEntity<?> getMiTypeDataByNumber(@PathVariable("number") String number){
        return arshinDataService.findMiTypeDataByNumber(number);
    }
}
