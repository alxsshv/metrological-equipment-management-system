package main.dto.rest.mappers;

import main.dto.rest.EntitySettingsDto;
import main.model.Settings;


public class SettingsDtoMapper {
    public static EntitySettingsDto mapToDto(Settings settings){
        EntitySettingsDto dto = new EntitySettingsDto();
        dto.setOrganizationNotation(settings.getOrganizationNotation());
        dto.setOrganizationTitle(settings.getOrganizationTitle());
        dto.setSignCipher(settings.getSignCipher());
        return dto;
    }

    public static Settings mapToEntity(EntitySettingsDto dto){
        Settings settings = new Settings();
        settings.setOrganizationNotation(dto.getOrganizationNotation());
        settings.setOrganizationTitle(dto.getOrganizationTitle());
        settings.setSignCipher(dto.getSignCipher());
        return settings;
    }
}
