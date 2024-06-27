package main.service.implementations;

import lombok.extern.slf4j.Slf4j;
import main.arshin.ArshinHttpClient;
import main.arshin.MiTypeRequestBuilder;
import main.arshin.entities.mit.MitItem;
import main.exception.ArshinResponseException;
import main.service.ServiceMessage;
import main.service.interfaces.ArshinDataService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class ArshinDataServiceImpl implements ArshinDataService {
    private final String mitUri;

    public ArshinDataServiceImpl(@Value("${arshin.mit.uri}") String mitUri) {
        this.mitUri = mitUri;
    }

    @Override
    public ResponseEntity<?> findMiTypeDataByNumber(String number){
        try {
            String miTypeRequest = new MiTypeRequestBuilder().uri(mitUri).number(number).build();
            MitItem miTypeData = ArshinHttpClient.getMiTypeItemIfOnlyMatches(miTypeRequest);
            return ResponseEntity.ok(miTypeData);
        } catch (ArshinResponseException ex){
            log.warn(ex.getMessage());
            return ResponseEntity.status(400).body(new ServiceMessage(ex.getMessage()));
        }
    }
}
