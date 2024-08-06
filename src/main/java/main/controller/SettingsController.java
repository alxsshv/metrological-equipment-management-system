package main.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import main.dto.rest.SettingsDto;
import main.service.ServiceMessage;
import main.service.interfaces.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/entity-settings")
public class SettingsController {
    @Autowired
    private SettingsService settingsService;

    @GetMapping
    public ResponseEntity<?> getSettings() {
        return ResponseEntity.ok(settingsService.get());
    }

    @PutMapping
    public ResponseEntity<?> updateSettings(@RequestBody SettingsDto settingsDto) {
        settingsService.saveOrUpdate(settingsDto);
        String okMessage = "Настройки сохранены";
        log.info(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }
}
