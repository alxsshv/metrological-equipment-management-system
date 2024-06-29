package main.dto.xml.arshin.factory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import main.config.AppConstants;
import main.dto.xml.arshin.Conditions;
import main.model.VerificationRecord;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@AllArgsConstructor
public class ConditionsFactory {

    public Conditions createConditions(VerificationRecord record){
        Conditions conditions = new Conditions();
        conditions.setHumidity(record.getHumidity() +" "+ AppConstants.HUMIDITY_UNIT);
        conditions.setPressure(record.getPressure() +" "+ AppConstants.PRESSURE_UNIT);
        conditions.setTemperature(record.getTemperature() +" "+ AppConstants.TEMPERATURE_UNIT);
        return conditions;
    }
}
