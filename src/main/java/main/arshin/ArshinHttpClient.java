package main.arshin;

import lombok.Setter;
import main.arshin.entities.Response;
import main.arshin.entities.VriItem;
import main.exception.ArshinResponseException;
import org.springframework.web.client.RestClient;


@Setter
public class ArshinHttpClient {


    public static VriItem getVerificationResults(String verificationRequest) throws ArshinResponseException {
        RestClient restClient = RestClient.create();
        Response response = restClient.get().uri(verificationRequest).retrieve().body(Response.class);
        if (response != null && response.getResult().getCount() == 1) {
            return response.getResult().getItems().get(0);
        } else {
            throw new ArshinResponseException("Количество полученных записей ФГИС Аршин = " + response.getResult().getCount()
                    + " что не позволяет однозначно определить какая запись соответствует запросу");
        }
    }


}
