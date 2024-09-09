package main.service.interfaces;

import main.model.VerificationReport;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface XMLFileWriter {
    void writeXMLReportFile(VerificationReport report) throws IOException;
    String writeXMLReportFileByReportList(List<VerificationReport> readyToSendReportList) throws FileNotFoundException;
}
