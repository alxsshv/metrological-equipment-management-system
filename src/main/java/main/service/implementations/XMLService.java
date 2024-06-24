package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.Setter;
import main.dto.xml.arshin.VerificationApplication;
import main.dto.xml.arshin.factory.VerificationApplicationFactory;
import main.dto.xml.fsa.FsaVerificationMessage;
import main.dto.xml.fsa.factory.VerificationMessageFactory;
import main.model.VerificationReport;
import main.repository.VerificationReportRepository;
import main.service.ServiceMessage;
import main.service.interfaces.IXMLService;
import main.service.utils.FileReader;
import main.service.utils.JaxbWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

@Getter
@Setter
@Service

public class XMLService implements IXMLService {
    public static final Logger log = LoggerFactory.getLogger(XMLService.class);
    private final String tempFileUploadPath;
    @Autowired
    private final VerificationReportRepository reportRepository;
    @Autowired
    private final VerificationApplicationFactory applicationFactory;
    @Autowired
    private final JaxbWriter jaxbWriter;


    public XMLService(@Value("${upload.temp.path}") String tempFileUploadPath,VerificationReportRepository reportRepository, VerificationApplicationFactory applicationFactory, JaxbWriter jaxbWriter) {
        this.tempFileUploadPath = tempFileUploadPath;
        this.reportRepository = reportRepository;
        this.applicationFactory = applicationFactory;
        this.jaxbWriter = jaxbWriter;
    }

    @Override
    public ResponseEntity<?> getXMLFileForArshin(long reportId) {
        String fileName = "ArshinReport" + reportId + ".xml";
        byte[] fileBytes;
        try {
            writeXMLFileForArshin(reportId, fileName);
            fileBytes = FileReader.readBytesFromFile(tempFileUploadPath, fileName);
        } catch(IOException | EntityNotFoundException ex){
        log.error(ex.getMessage());
        return ResponseEntity.status(500).body(new ServiceMessage(ex.getMessage()));
        }
        log.info("Файл {} передан", fileName);
        return ResponseEntity.ok()
                .header("Content-Disposition" , "attachment; filename=\""+ fileName +"\"")
                .body(fileBytes);
    }

    private void writeXMLFileForArshin(Long reportId,String fileName) throws FileNotFoundException {
        Optional<VerificationReport> reportOpt = reportRepository.findById(reportId);
        if (reportOpt.isEmpty()){
            throw new EntityNotFoundException("Отчет о поверке № " + reportId + " не найден");
        }
        VerificationApplication application = applicationFactory.createApplication(reportOpt.get());
        jaxbWriter.writeXMLForArshin(application, fileName);
    }

    @Override
    public ResponseEntity<?> getXMLFileForFSA(long reportId) {
        String fileName = "FSAReport" + reportId + ".xml";
        byte[] fileBytes;
        try {
            writeXMLFileForFSA(reportId, fileName);
            fileBytes = FileReader.readBytesFromFile(tempFileUploadPath, fileName);
        } catch(IOException | EntityNotFoundException ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(500).body(new ServiceMessage(ex.getMessage()));
        }
        log.info("Файл {} передан", fileName);
        return ResponseEntity.ok()
                .header("Content-Disposition" , "attachment; filename=\""+ fileName +"\"")
                .body(fileBytes);
    }


    private void writeXMLFileForFSA(Long reportId,String fileName) throws FileNotFoundException {
        Optional<VerificationReport> reportOpt = reportRepository.findById(reportId);
        if (reportOpt.isEmpty()){
            throw new EntityNotFoundException("Отчет о поверке № " + reportId + " не найден");
        }
        FsaVerificationMessage message = VerificationMessageFactory
                .createVerificationMessage(reportOpt.get());
        jaxbWriter.writeXMLForFSA(message, fileName);
    }





}
