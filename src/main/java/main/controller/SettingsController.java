package main.controller;

import lombok.AllArgsConstructor;
import main.dto.rest.SettingsDto;
import main.service.interfaces.ISettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/settings")
public class SettingsController {
    @Autowired
    private ISettingsService settingsService;

    @GetMapping
    public ResponseEntity<?> getSettings() {
        return settingsService.get();
    }

    @PutMapping
    public ResponseEntity<?> updateSettings(@RequestBody SettingsDto settingsDto) {
        return settingsService.saveOrUpdate(settingsDto);
    }
}
