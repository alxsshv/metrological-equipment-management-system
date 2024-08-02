package main.dto.xml.arshin.factory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import main.dto.xml.arshin.Result;
import main.dto.xml.arshin.VerificationApplication;
import main.model.VerificationRecord;
import main.model.VerificationReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Setter
@Getter
@AllArgsConstructor
public class VerificationApplicationFactory {
    @Autowired
    private ResultFactory resultFactory;

    public VerificationApplication createApplicationByReport(VerificationReport report){
        VerificationApplication application = new VerificationApplication();
        for (VerificationRecord record: report.getRecords()) {
            Result result = resultFactory.createResult(record);
            application.getResults().add(result);
        }
        return application;
    }

    public VerificationApplication createApplicationByReportList(List<VerificationReport> readyToSendReportList){
        VerificationApplication application = new VerificationApplication();
        for (VerificationReport report : readyToSendReportList){
            for (VerificationRecord record: report.getRecords()) {
                Result result = resultFactory.createResult(record);
                application.getResults().add(result);
            }
        }
        return application;
    }


}
