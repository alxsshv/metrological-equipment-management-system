package main.service.implementations;

import lombok.Getter;
import lombok.Setter;
import main.model.VerificationReport;
import main.service.interfaces.ReportFileService;
import main.service.interfaces.VerificationReportService;
import main.service.interfaces.VerificationReportStatusService;
import main.service.interfaces.XMLFileWriter;
import main.service.utils.VerificationReportListFsaLimitCutter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Getter
@Setter
@Service
public class FSAReportServiceFacade {
    @Autowired
    private VerificationReportService verificationReportService;
    @Autowired
    @Qualifier("fsa")
    private XMLFileWriter xmlFileWriter;
    @Autowired
    private ReportFileService reportFileService;
    @Autowired
    private VerificationReportStatusService verificationReportStatusService;
    private int maxFileRecordsLimit;

    public FSAReportServiceFacade(@Value("${fsa.max-file-records-limit}") int maxFileRecordsLimit, VerificationReportService verificationReportService,
                                  @Qualifier("fsa") XMLFileWriter xmlFileWriter,
                                  ReportFileService reportFileService,
                                  VerificationReportStatusService verificationReportStatusService) {
        this.maxFileRecordsLimit = maxFileRecordsLimit;
        this.verificationReportService = verificationReportService;
        this.xmlFileWriter = xmlFileWriter;
        this.reportFileService = reportFileService;
        this.verificationReportStatusService = verificationReportStatusService;
    }

    public ResponseEntity<?> createReportFileById(long reportId) throws IOException {
        VerificationReport report = verificationReportService.getReportById(reportId);
        xmlFileWriter.writeXMLReportFile(report);
        verificationReportStatusService.setSentToFsaStatus(report);
        return reportFileService.getReportFile(reportId);
    }

    public ResponseEntity<?> createReportFileForPublicToArshinReports() throws FileNotFoundException {
        List<VerificationReport> publicToArshinReportList = verificationReportService
                .getPublicToArshinReportsAndNotSendToFsa();
        List<VerificationReport> limitedReadyToFsaSendReportList = VerificationReportListFsaLimitCutter
                .cutByLimit(publicToArshinReportList,maxFileRecordsLimit);
        String filename = xmlFileWriter.writeXMLReportFileByReportList(limitedReadyToFsaSendReportList);
        verificationReportStatusService.setSentToFsaStatusForReports(limitedReadyToFsaSendReportList);
        return reportFileService.getReportFile(filename);
    }







}
