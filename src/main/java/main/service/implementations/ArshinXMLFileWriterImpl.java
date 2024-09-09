package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import main.dto.xml.arshin.VerificationApplication;
import main.dto.xml.arshin.factory.VerificationApplicationFactory;
import main.model.VerificationReport;
import main.service.Category;
import main.service.interfaces.XMLFileWriter;
import main.service.utils.ArshinJaxbWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Getter
@Setter
@Slf4j
@AllArgsConstructor
@Component
@Qualifier("arshin")
public class ArshinXMLFileWriterImpl implements XMLFileWriter {
    @Autowired
    private ReportFileServiceImpl reportFileService;
    @Autowired
    private VerificationApplicationFactory applicationFactory;
    @Autowired
    private ArshinJaxbWriter arshinJaxbWriter;


    public void writeXMLReportFile(VerificationReport report) throws IOException {
        VerificationApplication application = applicationFactory.createApplicationByReport(report);
        String storageFilename = reportFileService.addReportFile(Category.ARSHIN_REPORT,List.of(report));
        try {
            arshinJaxbWriter.writeXMLFile(application, storageFilename);
        } catch (FileNotFoundException ex) {
            reportFileService.delete(report.getId());
            throw new FileNotFoundException(ex.getMessage());
        }
    }


    public String writeXMLReportFileByReportList(List<VerificationReport> readyToSendReportList) throws FileNotFoundException {
        if (readyToSendReportList.isEmpty()){
            throw new EntityNotFoundException("Данных, подготовленных для передачи в ФГИС \"Аршин\" не найдено");
        }
        String storageFilename = reportFileService.addReportFile(Category.ARSHIN_REPORT, readyToSendReportList);
        VerificationApplication application = applicationFactory.createApplicationByReportList(readyToSendReportList);
        arshinJaxbWriter.writeXMLFile(application, storageFilename);
        return storageFilename;
    }










}
