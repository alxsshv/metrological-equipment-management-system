package main.dto.xml.fsa.factory;

import main.dto.xml.fsa.FsaVerificationMessage;
import main.dto.xml.fsa.VerificationMiData;
import main.model.VerificationRecord;
import main.model.VerificationReport;

import java.util.List;

public class VerificationMessageFactory {
    private static final int READY_TO_SEND = 2;

    public static FsaVerificationMessage createVerificationMessageByReport(VerificationReport report){
        FsaVerificationMessage message = new FsaVerificationMessage();
        VerificationMiData miData = new VerificationMiData();
        for (VerificationRecord record : report.getRecords()){
            miData.add(VerificationMIFactory.createVerificationMi(record));
        }
        message.setData(miData);
        message.setSaveMethod(READY_TO_SEND);
        return message;
    }

    public static FsaVerificationMessage createVerificationMessageByReportList(List<VerificationReport> publicToArshinReports){
        FsaVerificationMessage message = new FsaVerificationMessage();
        VerificationMiData miData = new VerificationMiData();
        for (VerificationReport report : publicToArshinReports) {
            for (VerificationRecord record : report.getRecords()) {
                miData.add(VerificationMIFactory.createVerificationMi(record));
            }
        }
        message.setData(miData);
        message.setSaveMethod(READY_TO_SEND);
        return message;
    }

}
