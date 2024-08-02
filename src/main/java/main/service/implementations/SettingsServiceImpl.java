package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import main.dto.rest.SettingsDto;
import main.dto.rest.mappers.SettingsDtoMapper;
import main.exception.DtoCompositionException;
import main.model.Settings;
import main.repository.SettingsRepository;
import main.service.interfaces.SettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class SettingsServiceImpl implements SettingsService {
    public static final Logger log = LoggerFactory.getLogger(SettingsServiceImpl.class);
    private final SettingsRepository settingsRepository;


    @Override
    public void saveOrUpdate(SettingsDto settingsDto) {
        Settings settings;
            checkSettingsDtoComposition(settingsDto);
            try {
                settings = getSettings();
                Settings updateSettings = SettingsDtoMapper.mapToEntity(settingsDto);
                settings.updateFrom(updateSettings);
            } catch (EntityNotFoundException ex){
                settings = SettingsDtoMapper.mapToEntity(settingsDto);
            }
            settingsRepository.save(settings);
    }


    private void checkSettingsDtoComposition(SettingsDto dto) throws DtoCompositionException {
        if (dto.getOrganizationNotation() == null || dto.getOrganizationNotation().isEmpty()){
            throw new DtoCompositionException("Пожалуйста заполните краткое наименование организации," +
                    " эксплуатирующей данную систему метрологического обеспечения");
        }
        if (dto.getOrganizationTitle() == null || dto.getOrganizationTitle().isEmpty()){
            throw new DtoCompositionException("Пожалуйста заполните полное наименование организации," +
                    " эксплуатирующей даннусю систему метрологического обеспечения");
        }
        if (dto.getSignCipher() == null || dto.getSignCipher().length() < 3){
            throw new DtoCompositionException("Убедитесь в правильности введенного условного шифра знака поверки");
        }
    }

    @Override
    public SettingsDto get() {
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
