package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.Setter;
import main.dto.rest.mappers.VerificationReportDtoMapper;
import main.dto.xml.arshin.VerificationApplication;
import main.dto.xml.arshin.factory.VerificationApplicationFactory;
import main.dto.xml.fsa.FsaVerificationMessage;
import main.dto.xml.fsa.factory.VerificationMessageFactory;
import main.model.VerificationReport;
import main.service.ServiceMessage;
import main.service.interfaces.VerificationReportService;
import main.service.interfaces.XMLService;
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
import java.util.List;

@Getter
@Setter
@Service

public class XMLServiceImpl implements XMLService {
    public static final Logger log = LoggerFactory.getLogger(XMLServiceImpl.class);
    private final String tempFileUploadPath;
    @Autowired
    private final VerificationReportService reportService;
    @Autowired
    private final VerificationApplicationFactory applicationFactory;
    @Autowired
    private final JaxbWriter jaxbWriter;


    public XMLServiceImpl(@Value("${upload.temp.path}") String tempFileUploadPath, VerificationReportService reportService, VerificationApplicationFactory applicationFactory, JaxbWriter jaxbWriter) {
        this.tempFileUploadPath = tempFileUploadPath;
        this.reportService = reportService;
        this.applicationFactory = applicationFactory;
        this.jaxbWriter = jaxbWriter;
    }

    @Override
    public ResponseEntity<?> getXMLFileForArshinByReport(long reportId) {
        try {
            String fileName = writeXMLFileForArshin(reportId);
            byte[] fileBytes = FileReader.readBytesFromFile(tempFileUploadPath, fileName);
            log.info("Файл {} передан", fileName);
            return ResponseEntity.ok()
                    .header("Content-Disposition" , "attachment; filename=\""+ fileName +"\"")
                    .body(fileBytes);
        } catch(IOException | EntityNotFoundException ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(500).body(new ServiceMessage(ex.getMessage()));
        }
    }

    private String writeXMLFileForArshin(Long reportId) throws FileNotFoundException {
        String fileName = "ArshinReport" + reportId + ".xml";
        VerificationReport report = reportService.getReportById(reportId);
        VerificationApplication application = applicationFactory.createApplicationByReport(report);
        jaxbWriter.writeXMLForArshin(application, fileName);
        setSentToArshinStatusForReport(report);
        return fileName;
    }

    @Override
    public ResponseEntity<?> getXMLFileForArshinByReadyToSendReports() {
        try {
            String fileName = writeXMLFileForArshinByReportList();
            byte[] fileBytes = FileReader.readBytesFromFile(tempFileUploadPath, fileName);
            log.info("Файл {} передан", fileName);
            return ResponseEntity.ok()
                    .header("Content-Disposition" , "attachment; filename=\""+ fileName +"\"")
                    .body(fileBytes);
        } catch(IOException | EntityNotFoundException ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(500).body(new ServiceMessage(ex.getMessage()));
        }
    }

    private void setSentToArshinStatusForReport(VerificationReport report){
            report.setReadyToSend(false);
            report.setSentToArshin(true);
            reportService.update(VerificationReportDtoMapper.mapToFullDto(report));
    }

    private String writeXMLFileForArshinByReportList() throws FileNotFoundException {
        List<VerificationReport> readyToSendReportList = reportService.getReadyToSendReports();
        if (readyToSendReportList.isEmpty()){
            throw new EntityNotFoundException("Данных, подготовленных для передачи в ФГИС \"Аршин\" не найдено");
        }
        VerificationApplication application = applicationFactory.createApplicationByReportList(readyToSendReportList);
        String fileName = generateFileNameForArshinReport(readyToSendReportList);
        jaxbWriter.writeXMLForArshin(application, fileName);
        setSentToArshinStatusForReports(readyToSendReportList);
        return fileName;
    }

    private String generateFileNameForArshinReport(List<VerificationReport> reports){
        String fistReportNum = String.valueOf(reports.get(0).getId());
        String lastReportNum = String.valueOf(reports.get(reports.size()-1).getId());
        return "arshinReport_"+ fistReportNum + "_" + lastReportNum + ".xml";
    }

    private void setSentToArshinStatusForReports(List<VerificationReport> readyToSendReports){
        readyToSendReports.forEach(this::setSentToArshinStatusForReport);
    }

    @Override
    public ResponseEntity<?> getXMLFileForFSAByReportId(long reportId) {
        try {
            String fileName =  writeXMLFileForFSA(reportId);
            byte[] fileBytes = FileReader.readBytesFromFile(tempFileUploadPath, fileName);
            log.info("Файл {} передан", fileName);
            return ResponseEntity.ok()
                    .header("Content-Disposition" , "attachment; filename=\""+ fileName +"\"")
                    .body(fileBytes);
        } catch(IOException | EntityNotFoundException ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(500).body(new ServiceMessage(ex.getMessage()));
        }
    }


    private String writeXMLFileForFSA(Long reportId) throws FileNotFoundException {
        String fileName = "FSAReport" + reportId + ".xml";
        VerificationReport report = reportService.getReportById(reportId);
        FsaVerificationMessage message = VerificationMessageFactory
                .createVerificationMessageByReport(report);
        jaxbWriter.writeXMLForFSA(message, fileName);
        return fileName;
    }

    @Override
    public ResponseEntity<?> getXMLFileForFSAByPublicToArshinReports() {
        try {
            String fileName =  writeXMLFileForFSAByReportList();
            byte[] fileBytes = FileReader.readBytesFromFile(tempFileUploadPath, fileName);
            log.info("Файл {} передан", fileName);
            return ResponseEntity.ok()
                    .header("Content-Disposition" , "attachment; filename=\""+ fileName +"\"")
                    .body(fileBytes);
        } catch(IOException | EntityNotFoundException ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(500).body(new ServiceMessage(ex.getMessage()));
        }
    }

    private String writeXMLFileForFSAByReportList() throws FileNotFoundException {
        List<VerificationReport> publicToArshinReports = reportService.getPublicToArshinReportsAndNotSendToFsa();
        FsaVerificationMessage message = VerificationMessageFactory
                .createVerificationMessageByReportList(publicToArshinReports);
        String fileName = generateFileNameForFsaReport(publicToArshinReports);
        jaxbWriter.writeXMLForFSA(message, fileName);
        return fileName;
    }

    private String generateFileNameForFsaReport(List<VerificationReport> reports){
        String fistReportNum = String.valueOf(reports.get(0).getId());
        String lastReportNum = String.valueOf(reports.get(reports.size()-1).getId());
        return "fsaReport_"+ fistReportNum + "_" + lastReportNum + ".xml";
    }





}
