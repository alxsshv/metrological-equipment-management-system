package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import main.dto.xml.fsa.FsaVerificationMessage;
import main.dto.xml.fsa.factory.VerificationMessageFactory;
import main.model.VerificationReport;
import main.service.Category;
import main.service.interfaces.XMLFileWriter;
import main.service.utils.FSAJaxbWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Getter
@Setter
@Slf4j
@AllArgsConstructor
@Validated
@Component
@Qualifier("fsa")
public class FsaXMLFileWriterImpl implements XMLFileWriter {
    @Autowired
    private ReportFileServiceImpl reportFileService;
    @Autowired
    private FSAJaxbWriter fsaJaxbWriter;


    @Override
    public void writeXMLReportFile(VerificationReport report) throws IOException {
        FsaVerificationMessage message = VerificationMessageFactory
                .createVerificationMessageByReport(report);
        String storageFileName = reportFileService.addReportFile(Category.FSA_REPORT, List.of(report));
        try {
            fsaJaxbWriter.writeXMLFile(message,storageFileName);
        } catch (FileNotFoundException ex) {
            reportFileService.delete(report.getId());
            throw new FileNotFoundException(ex.getMessage());
        }
    }

    @Override
    public String writeXMLReportFileByReportList(List<VerificationReport> publicToArshinReportList) throws FileNotFoundException {
        if (publicToArshinReportList.isEmpty()){
            throw new EntityNotFoundException("Данных, подготовленных для передачи в ФСА не найдено");
        }
        String storageFileName = reportFileService.addReportFile(Category.FSA_REPORT, publicToArshinReportList);
        FsaVerificationMessage message = VerificationMessageFactory
                .createVerificationMessageByReportList(publicToArshinReportList);
        fsaJaxbWriter.writeXMLFile(message, storageFileName);
        return storageFileName;
    }


}
