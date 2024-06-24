package main.dto.xml.arshin.factory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import main.dto.xml.arshin.Result;
import main.model.VerificationRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@AllArgsConstructor
public class ResultFactory {
    @Autowired
    private MiInfoFactory miInfoFactory;
    @Autowired
    private VriInfoFactory vriInfoFactory;
    @Autowired
    private MeanFactory meanFactory;
    @Autowired
    private ConditionsFactory conditionsFactory;

    public Result createResult(VerificationRecord record){
        Result result = new Result();
        result.setMiInfo(miInfoFactory.createMiInfo(record));
        result.setVriInfo(vriInfoFactory.createVriInfo(record));
        result.setMeans(meanFactory.createMeans(record));
        result.setConditions(conditionsFactory.createConditions(record));
        return result;
    }
}
