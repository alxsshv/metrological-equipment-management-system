package main.arshin;

import lombok.Setter;
import main.arshin.entities.mit.MitItem;
import main.arshin.entities.mit.MitResponse;
import main.arshin.entities.vri.VriItem;
import main.arshin.entities.vri.VriResponse;
import main.exception.ArshinResponseException;
import org.springframework.web.client.RestClient;



@Setter
public class ArshinHttpClient {


    public static VriItem getVerificationItemIfOnlyMatches(String verificationRequest) throws ArshinResponseException {
        RestClient restClient = RestClient.create();
        VriResponse response = restClient.get().uri(verificationRequest).retrieve().body(VriResponse.class);
        if (response != null && response.getResult().getCount() == 1) {
            return response.getResult().getItems().get(0);
        } else {
            throw new ArshinResponseException("Количество полученных записей ФГИС Аршин = " + response.getResult().getCount()
                    + " что не позволяет однозначно определить какая запись соответствует запросу");
        }
    }

    public static MitItem getMiTypeItemIfOnlyMatches(String miTypeRequest) throws ArshinResponseException {
        RestClient restClient = RestClient.create();
        MitResponse response = restClient.get().uri(miTypeRequest).retrieve().body(MitResponse.class);
        if (response != null && response.getResult().getCount() == 1) {
            return response.getResult().getItems().get(0);
        } else {
            throw new ArshinResponseException("Количество полученных записей ФГИС Аршин = " + response.getResult().getCount()
                    + " что не позволяет однозначно определить какая запись соответствует запросу");
        }
    }


}
