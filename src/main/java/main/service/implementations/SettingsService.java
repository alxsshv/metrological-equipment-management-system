package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import main.dto.rest.SettingsDto;
import main.dto.rest.mappers.SettingsDtoMapper;
import main.exception.DtoCompositionException;
import main.model.Settings;
import main.repository.SettingsRepository;
import main.service.ServiceMessage;
import main.service.interfaces.ISettingsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class SettingsService implements ISettingsService {
    public static final Logger log = LoggerFactory.getLogger(SettingsService.class);
    private final SettingsRepository settingsRepository;


    @Override
    public ResponseEntity<?> saveOrUpdate(SettingsDto settingsDto) {
        Settings settings;
        try {
            checkSettingsDtoComposition(settingsDto);
            try {
                settings = getSettings();
                Settings updateSettings = SettingsDtoMapper.mapToEntity(settingsDto);
                settings.updateFrom(updateSettings);
            } catch (EntityNotFoundException ex){
                settings = SettingsDtoMapper.mapToEntity(settingsDto);
            }
            settingsRepository.save(settings);
            String okMessage = "Настройки сохранены";
            log.info(okMessage);
            return ResponseEntity.ok(new ServiceMessage(okMessage));
        } catch(DtoCompositionException ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(400).body(new ServiceMessage(ex.getMessage()));
        }
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
    public ResponseEntity<?> get() {
        try {
            SettingsDto settingsDto = SettingsDtoMapper.mapToDto(getSettings());
            return ResponseEntity.ok(settingsDto);
        } catch(EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
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
