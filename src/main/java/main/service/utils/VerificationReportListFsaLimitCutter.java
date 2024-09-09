package main.service.utils;

import main.model.VerificationReport;

import java.util.ArrayList;
import java.util.List;

public class VerificationReportListFsaLimitCutter {

    public static List<VerificationReport> cutByLimit(List<VerificationReport> publicToAsrhinAndNotSentToFsaList,
                                                       int maxFileRecordsLimit){
        List<VerificationReport> sizeLimitedList = new ArrayList<>();
        int recordsCounter = 0;
        for (VerificationReport report: publicToAsrhinAndNotSentToFsaList){
            if (recordsCounter + report.getRecords().size() <= maxFileRecordsLimit){
                sizeLimitedList.add(report);
                recordsCounter = recordsCounter + report.getRecords().size();
            } else {
                break;
            }
        }
        return sizeLimitedList;
    }
}
