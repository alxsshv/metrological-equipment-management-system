package main.service.interfaces;


import main.dto.rest.SettingsDto;
import main.model.Settings;

public interface SettingsService {
    void saveOrUpdate(SettingsDto settingsDto);
    SettingsDto get();
    Settings getSettings();

}
