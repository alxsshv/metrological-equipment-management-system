package main.service.interfaces;


import main.dto.rest.EntitySettingsDto;
import main.model.Settings;

public interface SettingsService {
    void saveOrUpdate(EntitySettingsDto entitySettingsDto);
    EntitySettingsDto get();
    Settings getSettings();

}
