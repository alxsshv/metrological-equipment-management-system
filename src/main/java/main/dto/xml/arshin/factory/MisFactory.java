package main.dto.xml.arshin.factory;


import main.dto.xml.arshin.Mi;
import main.dto.xml.arshin.Mis;
import main.model.MeasurementInstrument;
import main.model.VerificationRecord;

public class MisFactory {
    public static Mis createMis(VerificationRecord record){
        Mis mis = new Mis();
        for (MeasurementInstrument verificationMi : record.getVerificationMis()){
            Mi mi = new Mi();
            mi.setTypeNum(verificationMi.getMiType().getNumber());
            mi.setManufactureNum(verificationMi.getSerialNum());
            mis.getMiLIst().add(mi);
        }
        return mis;
    }
}
