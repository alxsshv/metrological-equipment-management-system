package main.dto.xml.fsa.factory;

import main.dto.xml.fsa.VerificationMI;
import main.dto.xml.fsa.factory.converter.ApplicableToResultVerificationConverter;
import main.dto.xml.fsa.factory.converter.ArshinVerificationNumToNumberVerificationConverter;
import main.model.VerificationRecord;

import java.time.format.DateTimeFormatter;

public class VerificationMIFactory {
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static VerificationMI createVerificationMi(VerificationRecord record){
        VerificationMI instrument = new VerificationMI();
        instrument.setNumberVerification(ArshinVerificationNumToNumberVerificationConverter
                .convert(record.getArshinVerificationNumber()));
        instrument.setTypeMeasuringInstrument(record.getMi().getModification());
        instrument.setDateVerification(record.getVerificationDate().format(dateFormatter));
        if (record.getValidDate() != null) {
            instrument.setDateEndVerification(record.getValidDate().format(dateFormatter));
        }
        instrument.setApprovedEmployee(ApprovedEmployeeFactory.createApprovedEmployee(record.getEmployee()));
        instrument.setResultVerification(ApplicableToResultVerificationConverter.convert(record.isApplicable()));
        return instrument;
    }
}
