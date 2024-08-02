package main.dto.xml.arshin.factory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import main.dto.xml.arshin.Mean;
import main.dto.xml.arshin.MiEta;
import main.model.MiStandard;
import main.model.VerificationRecord;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@Setter
@Getter
@AllArgsConstructor
public class MeanFactory {
    public Mean createMeans(VerificationRecord record){
        Mean mean = new Mean();
        mean.getMiEtaList().addAll(buildMietaList(record));
        if (!record.getVerificationMis().isEmpty()) {
            mean.setMis(MisFactory.createMis(record));
        }
        return mean;
    }

    private static List<MiEta> buildMietaList(VerificationRecord record){
        List<MiEta> miEtaList = new ArrayList<>();
        for (MiStandard standard : record.getMiStandards()){
            MiEta miEta = new MiEta();
            miEta.setNumber(standard.getArshinNumber());
            miEtaList.add(miEta);
        }
        return miEtaList;
    }
}
