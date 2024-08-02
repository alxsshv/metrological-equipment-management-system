package main.dto.xml.arshin.factory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import main.dto.xml.arshin.MiInfo;
import main.model.VerificationRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@AllArgsConstructor
public class MiInfoFactory {
    @Autowired
    private SingleMiFactory singleMiFactory;

    public MiInfo createMiInfo(VerificationRecord record){
        MiInfo miInfo = new MiInfo();
        miInfo.setSingleMi(singleMiFactory.createSingleMi(record));
        return miInfo;
    }
}
