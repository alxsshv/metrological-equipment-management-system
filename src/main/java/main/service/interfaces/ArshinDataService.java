package main.service.interfaces;

import org.springframework.http.ResponseEntity;

public interface ArshinDataService {
    ResponseEntity<?> findMiTypeDataByNumber(String number);
}
