package main.service.implementations;

import lombok.extern.slf4j.Slf4j;
import main.arshin.ArshinHttpClient;
import main.arshin.MiTypeRequestBuilder;
import main.arshin.VerificationRequestBuilder;
import main.arshin.entities.mit.MitItem;
import main.arshin.entities.vri.VriItem;
import main.exception.ArshinResponseException;
import main.model.VerificationRecord;
import main.service.ServiceMessage;
import main.service.interfaces.ArshinDataService;
import main.service.interfaces.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class ArshinDataServiceImpl implements ArshinDataService {
    private final String mitUri;
    private final String vriUri;
    @Autowired
    private final SettingsService settingsService;

    public ArshinDataServiceImpl(@Value("${arshin.mit.uri}") String mitUri,
                                 @Value("${arshin.verification.uri}") String vriUri,
                                 SettingsService settingsService) {
        this.mitUri = mitUri;
        this.vriUri = vriUri;
        this.settingsService = settingsService;
    }

    //TODO придумать класс-обертку, чтобы данный метод возвращал MitItem, а обертка возвращала ResponseEntity
    @Override
    public ResponseEntity<?> findMiTypeDataByNumber(String number){
        try {
            String miTypeRequest = new MiTypeRequestBuilder().uri(mitUri).number(number).build();
            MitItem miTypeData = ArshinHttpClient.getMiTypeItemIfOnlyMatches(miTypeRequest, 0);
            return ResponseEntity.ok(miTypeData);
        } catch (ArshinResponseException ex){
            log.warn(ex.getMessage());
            return ResponseEntity.status(400).body(new ServiceMessage(ex.getMessage()));
        }
    }

    public VriItem findVerificationRecordsData(VerificationRecord record) throws ArshinResponseException {
            String verificationOrganization = settingsService.getSettings().getOrganizationNotation();
            log.info("Получение данных ФГИС \"Аршин\" для записи о поверке №{}", record.getId());
            String verificationRequest = new VerificationRequestBuilder()
                    .uri(vriUri)
                    .miModification(record.getMi().getModification())
                    .miNumber(record.getMi().getSerialNum())
                    .orgTitle(verificationOrganization)
                    .verificationDate(record.getVerificationDate())
                    .build();
           return ArshinHttpClient.getVerificationItemIfOnlyMatches(verificationRequest, 0);
    }
}
