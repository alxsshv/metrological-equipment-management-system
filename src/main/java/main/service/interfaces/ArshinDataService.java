package main.service.interfaces;

import main.arshin.entities.vri.VriItem;
import main.exception.ArshinResponseException;
import main.model.VerificationRecord;
import org.springframework.http.ResponseEntity;

public interface ArshinDataService {
    VriItem findVerificationRecordsData(VerificationRecord record) throws ArshinResponseException;
    ResponseEntity<?> findMiTypeDataByNumber(String number);
}
