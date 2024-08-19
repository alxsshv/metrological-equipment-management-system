package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import main.dto.rest.EntitySettingsDto;
import main.dto.rest.mappers.SettingsDtoMapper;
import main.model.Settings;
import main.repository.SettingsRepository;
import main.service.interfaces.SettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;


@Service
@RequiredArgsConstructor
@Validated
public class SettingsServiceImpl implements SettingsService {
    public static final Logger log = LoggerFactory.getLogger(SettingsServiceImpl.class);
    private final SettingsRepository settingsRepository;


    @Override
    public void saveOrUpdate(@Validated EntitySettingsDto entitySettingsDto) {
        Settings settings;
            try {
                settings = getSettings();
                Settings updateSettings = SettingsDtoMapper.mapToEntity(entitySettingsDto);
                settings.updateFrom(updateSettings);
            } catch (EntityNotFoundException ex){
                settings = SettingsDtoMapper.mapToEntity(entitySettingsDto);
            }
            settingsRepository.save(settings);
    }


    @Override
    public EntitySettingsDto get() {
            return SettingsDtoMapper.mapToDto(getSettings());
    }

    @Override
    public Settings getSettings() {
        Optional<Settings> settingsOpt = settingsRepository.findById(1);
        if (settingsOpt.isEmpty()){
            throw new EntityNotFoundException("Настройки не установлены и заданы по умолчанию");
        }
        return settingsOpt.get();
    }



}
