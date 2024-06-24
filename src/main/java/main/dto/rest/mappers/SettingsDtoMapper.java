package main.dto.rest.mappers;

import main.dto.rest.SettingsDto;
import main.model.Settings;


public class SettingsDtoMapper {
    public static SettingsDto mapToDto(Settings settings){
        SettingsDto dto = new SettingsDto();
        dto.setOrganizationNotation(settings.getOrganizationNotation());
        dto.setOrganizationTitle(settings.getOrganizationTitle());
        dto.setSignCipher(settings.getSignCipher());
        return dto;
    }

    public static Settings mapToEntity(SettingsDto dto){
        Settings settings = new Settings();
        settings.setOrganizationNotation(dto.getOrganizationNotation());
        settings.setOrganizationTitle(dto.getOrganizationTitle());
        settings.setSignCipher(dto.getSignCipher());
        return settings;
    }
}
