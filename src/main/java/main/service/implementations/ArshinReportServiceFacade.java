package main.service.implementations;


import lombok.Getter;
import lombok.Setter;
import main.model.VerificationReport;
import main.service.interfaces.ReportFileService;
import main.service.interfaces.VerificationReportService;
import main.service.interfaces.VerificationReportStatusService;
import main.service.interfaces.XMLFileWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Getter
@Setter
@Service
public class ArshinReportServiceFacade {
    @Autowired
    private VerificationReportService verificationReportService;
    @Autowired
    @Qualifier("arshin")
    private XMLFileWriter xmlFileWriter;
    @Autowired
    private ReportFileService reportFileService;
    @Autowired
    private VerificationReportStatusService verificationReportStatusService;

    public ArshinReportServiceFacade(VerificationReportService verificationReportService,
                                     @Qualifier("arshin") XMLFileWriter xmlFileWriter,
                                     ReportFileService reportFileService,
                                     VerificationReportStatusService verificationReportStatusService) {
        this.verificationReportService = verificationReportService;
        this.xmlFileWriter = xmlFileWriter;
        this.reportFileService = reportFileService;
        this.verificationReportStatusService = verificationReportStatusService;
    }

    public ResponseEntity<?> createReportFileById(long reportId) throws IOException {
        VerificationReport report = verificationReportService.getReportById(reportId);
        xmlFileWriter.writeXMLReportFile(report);
        verificationReportStatusService.setSentToArshinStatus(report);
        return reportFileService.getReportFile(reportId);
    }

    public ResponseEntity<?> createReportFileForReadyToSentReports() throws FileNotFoundException {
        List<VerificationReport> readyToSendReportList = verificationReportService.getReadyToSendReports();
        String filename = xmlFileWriter.writeXMLReportFileByReportList(readyToSendReportList);
        verificationReportStatusService.setSentToArshinStatusForReports(readyToSendReportList);
        return reportFileService.getReportFile(filename);
    }




}
