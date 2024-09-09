package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import main.config.AppUploadPaths;
import main.dto.rest.ReportFileDto;
import main.dto.rest.mappers.ReportFileDtoMapper;
import main.model.ReportFile;
import main.model.VerificationReport;
import main.repository.ReportFileRepository;
import main.service.Category;
import main.service.ServiceMessage;
import main.service.interfaces.ReportFileService;
import main.service.utils.FileContentTypeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@Service
@RequiredArgsConstructor
public class ReportFileServiceImpl implements ReportFileService {
    private final static Logger log = LoggerFactory.getLogger(ReportFileServiceImpl.class);
    private final String extension = "xml";
    @Autowired
    private AppUploadPaths appUploadPaths;
    @Autowired
    private final ReportFileRepository reportFileRepository;


    @Override
    public String addReportFile(Category category, List<VerificationReport> verificationReports) {
                String fileName = generateFileName(category, verificationReports);
                String storageFilename = UUID.randomUUID() + "." + fileName;
                ReportFile reportFile = new ReportFile();
                reportFile.setStorageFileName(storageFilename);
                reportFile.setOriginalFileName(fileName);
                reportFile.setDescription(generateDescription(verificationReports));
                reportFile.setExtension(extension);
                reportFile.setCategoryName(category.name());
                reportFileRepository.save(reportFile);
                log.info("Файл {} успешно загружен на сервер", storageFilename);
                return storageFilename;
    }

    private String generateFileName(Category category, List<VerificationReport> reports){
        String fistReportNum = String.valueOf(reports.get(0).getId());
        String lastReportNum = String.valueOf(reports.get(reports.size()-1).getId());
        return category.name()+ "_" + fistReportNum + "_" + lastReportNum + "." + extension;
    }

    private String generateDescription(List<VerificationReport> reports){
        String fistReportNum = String.valueOf(reports.get(0).getId());
        String lastReportNum = String.valueOf(reports.get(reports.size()-1).getId());
        return "Отчеты о поверке с " + fistReportNum + " по " + lastReportNum;
    }

    @Override
    public ReportFile findFileById(long id){
        Optional<ReportFile> reportFileOpt = reportFileRepository.findById(id);
        if(reportFileOpt.isEmpty()){
            throw new EntityNotFoundException("Файл отчета № "+ id +" не найден");
        }
        return reportFileOpt.get();
    }

    public ReportFile findFileByStorageFileName(String storageFileName){
        Optional<ReportFile> reportFileOpt = reportFileRepository.findByStorageFileName(storageFileName);
        if(reportFileOpt.isEmpty()){
            throw new EntityNotFoundException("Файл отчета "+ storageFileName +" не найден");
        }
        return reportFileOpt.get();
    }

    @Override
    public ResponseEntity<?> getReportFile(Long id) {
        try {
            ReportFile reportFile = findFileById(id);
            ResponseEntity<?> responseEntity = buildResponseEntityFrom(reportFile);
            String okMessage = "Файл " + reportFile.getStorageFileName() + " передан";
            log.info(okMessage);
            return responseEntity;
        } catch (IOException ex) {
            String errorMessage = "Файл не найден или поврежден. ";
            log.error("{}:{}", errorMessage, ex.getMessage());
            return ResponseEntity.status(500).body(new ServiceMessage(errorMessage));
        }
    }

    public ResponseEntity<?> getReportFile(String fileName) {
        try {
            ReportFile reportFile = findFileByStorageFileName(fileName);
            ResponseEntity<?> responseEntity = buildResponseEntityFrom(reportFile);
            String okMessage = "Файл " + reportFile.getStorageFileName() + " передан";
            log.info(okMessage);
            return responseEntity;
        } catch (IOException ex) {
            String errorMessage = "Файл не найден или поврежден. ";
            log.error("{}:{}", errorMessage, ex.getMessage());
            return ResponseEntity.status(500).body(new ServiceMessage(errorMessage));
        }
    }

        private ResponseEntity<?> buildResponseEntityFrom(ReportFile reportFile) throws IOException {
        ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                .filename(reportFile.getOriginalFileName(), StandardCharsets.UTF_8).build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(FileContentTypeBuilder.getContentType(reportFile.getExtension()));
        headers.setContentDisposition(contentDisposition);
        return ResponseEntity.ok()
                .headers(headers)
                .body(new ByteArrayResource(Files.readAllBytes(Path.of(appUploadPaths.getReportsPath() + reportFile.getStorageFileName()))));
    }


    @Override
    public Page<ReportFileDto> getReports(Category category, Pageable pageable){
        Page<ReportFile> reportFiles = reportFileRepository.findByCategoryName(category.name(),pageable);
        return reportFiles.map(ReportFileDtoMapper::mapToDto);
    }


    @Override
    public void delete(long id) throws IOException {
        ReportFile reportFile = findFileById(id);
        Files.deleteIfExists(Path.of(appUploadPaths.getReportsPath() + "/" + reportFile.getStorageFileName()));
        reportFileRepository.delete(reportFile);
    }






}





