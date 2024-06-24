package main.dto.xml.arshin.factory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import main.dto.xml.arshin.VriInfo;
import main.model.MiTypeInstruction;
import main.model.Settings;
import main.model.VerificationRecord;
import main.service.implementations.MiTypeService;
import main.service.implementations.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VriInfoFactory {
    @Autowired
    private MiTypeService miTypeService;
    @Autowired
    private SettingsService settingsService;
    @Autowired
    private ApplicabilityFactory applicabilityFactory;

    public VriInfo createVriInfo(VerificationRecord record){
        Settings settings = settingsService.getSettings();
        MiTypeInstruction instruction = miTypeService.getInstructionById(record.getMi().getMiType().getId());
        VriInfo vriInfo = new VriInfo();
        vriInfo.setOrganization(settings.getOrganizationNotation());
        vriInfo.setSignCipher(settings.getSignCipher());
        vriInfo.setMiOwner(record.getMi().getOwner().getNotation());
        vriInfo.setVrfDate(formatDateToXMLString(record.getVerificationDate()));
        vriInfo.setValidDate(formatDateToXMLString(record.getValidDate()));
        vriInfo.setVriType(String.valueOf(record.getVerificationType()));
        vriInfo.setDocTitle(instruction.getInstructionTitle());
        if (record.isApplicable()){
            vriInfo.setApplicable(applicabilityFactory.createApplicable());
        } else vriInfo.setInapplicable(applicabilityFactory.createInapplicable(record));
        return vriInfo;
    }

    private String formatDateToXMLString(LocalDate date){
        if (date == null){
            return null;
        }
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
