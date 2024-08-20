package main.dto.xml.arshin.factory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import main.dto.xml.arshin.BriefProcedure;
import main.dto.xml.arshin.Result;
import main.model.MiTypeDetails;
import main.model.Settings;
import main.model.VerificationRecord;
import main.service.interfaces.MiTypeDetailsService;
import main.service.interfaces.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@Getter
@Setter
@AllArgsConstructor
public class ResultFactory {
    @Autowired
    private MiInfoFactory miInfoFactory;
    @Autowired
    private MeanFactory meanFactory;
    @Autowired
    private ConditionsFactory conditionsFactory;
    @Autowired
    private SettingsService settingsService;
    @Autowired
    private ApplicabilityFactory applicabilityFactory;
    @Autowired
    private MiTypeDetailsService miTypeDetailsService;

    public Result createResult(VerificationRecord record){
        Settings settings = settingsService.getSettings();
        MiTypeDetails instruction = miTypeDetailsService.getById(record.getMi().getMiType().getId());

        Result result = new Result();
        result.setMiInfo(miInfoFactory.createMiInfo(record));
        result.setSignCipher(settings.getSignCipher());
        result.setMiOwner(buildMiOwner(record));
        result.setVrfDate(formatDateToXMLString(record.getVerificationDate()));
        result.setValidDate(formatDateToXMLString(record.getValidDate()));
        result.setVriType(record.getVerificationType());
        result.setCalibration(record.isCalibration());
        if (record.isApplicable()){
           result.setApplicable(applicabilityFactory.createApplicable(record));
        } else result.setInapplicable(applicabilityFactory.createInapplicable(record));
        result.setDocTitle(instruction.getInstructionNotation());
        result.setMetrologist(buildMetrologist(record));
        result.setMeans(meanFactory.createMeans(record));
        result.setConditions(conditionsFactory.createConditions(record));
        if (record.isShortVerification()){
            result.setBriefProcedure(new BriefProcedure(record.getShortVerificationCharacteristic()));
        }
        return result;
    }

    private String buildMiOwner(VerificationRecord record){
        if (record.getMi().getOwner() != null) {
            return record.getMi().getOwner().getNotation();
        } else return "Не указан";
    }

    private String formatDateToXMLString(LocalDate date){
        if (date == null){
            return null;
        }
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+"+03:00";
    }

    private String buildMetrologist(VerificationRecord record){
        return record.getEmployee().getSurname() + " "
                + record.getEmployee().getName() + " "
                + record.getEmployee().getPatronymic();
    }



}
