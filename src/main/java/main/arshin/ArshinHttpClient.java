package main.arshin;

import lombok.Setter;

import main.arshin.entities.Response;
import main.arshin.entities.mit.MitItem;
import main.arshin.entities.mit.MitResponse;
import main.arshin.entities.vri.VriItem;
import main.arshin.entities.vri.VriResponse;
import main.exception.ArshinResponseException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;



@Setter
public class ArshinHttpClient {


    public static VriItem getVerificationItemIfOnlyMatches(String verificationRequest) throws ArshinResponseException {
        try {
            RestClient restClient = RestClient.create();
            VriResponse response = restClient.get().uri(verificationRequest).retrieve().body(VriResponse.class);
            if (response != null && response.getResult().getCount() == 1) {
                return response.getResult().getItems().get(0);
            }
            throw new ArshinResponseException(getErrorMessage(response));
        } catch (ResourceAccessException ex){
            throw new ArshinResponseException("Ошибка соединения с сервером ФГИС\"Аршин\". Проверьте интернет соединение и доступность серверов ФГИС \"Аршин\"");
        }
    }

    public static MitItem getMiTypeItemIfOnlyMatches(String miTypeRequest) throws ArshinResponseException {
        try {
            RestClient restClient = RestClient.create();
            MitResponse response = restClient.get().uri(miTypeRequest).retrieve().body(MitResponse.class);
            if (response != null && response.getResult().getCount() == 1) {
                return response.getResult().getItems().get(0);
             }
            throw new ArshinResponseException(getErrorMessage(response));
        } catch (ResourceAccessException ex){
            throw new ArshinResponseException("Ошибка соединения с сервером ФГИС\"Аршин\". Проверьте интернет соединение и доступность серверов ФГИС \"Аршин\"");
        }
    }

    private static String getErrorMessage(Response response){
        if (response == null){
            return "Сервер ФГИС \"Аршин\" не отвечает";
        }
        if (response.getResult().getCount() == 0){
            return "По указанному запросу записей в ФГИС \"Аршин\" не найдено";
        }
        if (response.getResult().getCount() > 1){
            return "Количество полученных записей из ФГИС \"Аршин\" = " + response.getResult().getCount()
                    + " что не позволяет однозначно определить какая запись соответствует запросу";
        }
        return "Ошибка чтения данных полученных от ФГИС \"Аршин\"";
    }



}
