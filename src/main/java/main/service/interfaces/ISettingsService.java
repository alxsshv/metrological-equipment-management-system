package main.service.interfaces;


import main.dto.rest.SettingsDto;
import main.model.Settings;
import org.springframework.http.ResponseEntity;

public interface ISettingsService {
    ResponseEntity<?> saveOrUpdate(SettingsDto settingsDto);
    ResponseEntity<?> get();
    Settings getSettings();

}
