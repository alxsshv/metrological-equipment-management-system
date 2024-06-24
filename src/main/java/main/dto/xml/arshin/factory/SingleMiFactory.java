package main.dto.xml.arshin.factory;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import main.dto.xml.arshin.SingleMi;
import main.model.VerificationRecord;
import org.springframework.stereotype.Component;

//TODO: реализовать возможность поверки си применяемого в качестве эталона и партии си и
// реализовать в данном классе методы createEtaMI и createPartyMi и селектор
@Component
@Getter
@Setter
@AllArgsConstructor
public class SingleMiFactory {

    public SingleMi createSingleMi(VerificationRecord record) {
        SingleMi singleMi = new SingleMi();
        singleMi.setMiTypeNumber(record.getMi().getMiType().getNumber());
        singleMi.setMiTypeTitle(record.getMi().getMiType().getTitle());
        singleMi.setManufactureNum(getNullIfSerialNumContentMaskSymbol(record));
        singleMi.setInventoryNum(record.getMi().getInventoryNum());
        singleMi.setModification(record.getMi().getModification());
        return singleMi;
    }

    private static String getNullIfSerialNumContentMaskSymbol(VerificationRecord record){
        if (record.getMi().getSerialNum().contains("*") && record.getMi().getInventoryNum()!= null){
            return record.getMi().getInventoryNum();
        }
        return record.getMi().getSerialNum();
    }


}
