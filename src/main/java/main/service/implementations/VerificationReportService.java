package main.service.implementations;

import main.dto.rest.VerificationReportDto;
import main.dto.rest.VerificationReportFullDto;
import main.dto.rest.mappers.VerificationRecordDtoMapper;
import main.dto.rest.mappers.VerificationReportDtoMapper;
import main.model.VerificationRecord;
import main.model.VerificationReport;
import main.repository.VerificationReportRepository;
import main.service.ServiceMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerificationReportService {
    private static final Logger log = LoggerFactory.getLogger(VerificationReportService.class);
    private final VerificationReportRepository reportRepository;

    public VerificationReportService(VerificationReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public ResponseEntity<?> save(VerificationReportFullDto reportDto){
        String errorMessage = checkVerificationReportDtoComposition(reportDto);
        if (!errorMessage.isEmpty()) {
            log.info(errorMessage);
            return ResponseEntity.status(422).body(
                    new ServiceMessage(errorMessage));
        }
        VerificationReport report = VerificationReportDtoMapper.mapToEntity(reportDto);
        reportRepository.save(report);
        String okMessage = "Отчет о поверке сохранен";
        log.info(okMessage);
        return ResponseEntity.status(201).body(new ServiceMessage(okMessage));
    }

    public ResponseEntity<?> update(VerificationReportFullDto reportDto){
        String errorMessage = checkVerificationReportDtoComposition(reportDto);
        if (!errorMessage.isEmpty()) {
            log.info(errorMessage);
            return ResponseEntity.status(422).body(
                    new ServiceMessage(errorMessage));
        }
        Optional<VerificationReport> reportFromDBOpt = reportRepository.findById(reportDto.getId());
        if(reportFromDBOpt.isEmpty()){
            errorMessage = "Отчет для обновления не найден";
            log.info(errorMessage + " id = " + reportDto.getId());
            return ResponseEntity.status(404).body(
                    new ServiceMessage(errorMessage));
        }
        VerificationReport reportFromDb = reportFromDBOpt.get();
        reportDto.getRecords().forEach(recordDto -> {
            VerificationRecord record = VerificationRecordDtoMapper.mapToEntity(recordDto);
            if (!reportFromDb.getRecords().contains(record)){
                reportFromDb.addRecord(record);
            }
        });
        reportRepository.save(reportFromDb);
        String okMessage = "Отчет о поверке сохранен";
        log.info(okMessage);
        return ResponseEntity.ok().body(new ServiceMessage(okMessage));
    }

    private String checkVerificationReportDtoComposition(VerificationReportFullDto reportDto){
        if (reportDto.getRecords().isEmpty()){
            return "Отчет должен содержать хотябы одну запись о поверке средства измерений";
        }
        return "";
    }

    public Page<VerificationReportDto> getAllWithPagination(Pageable pageable){
        return reportRepository.findAll(pageable).map(VerificationReportDtoMapper::mapToDto);
    }

    public ResponseEntity<?> getById(long id){
        Optional<VerificationReport> reportOpt = reportRepository.findById(id);
        if (reportOpt.isEmpty()){
            String errorMessage = "Отчет № " + id + "не найден";
            log.info(errorMessage);
            return ResponseEntity.status(404).body(new ServiceMessage(errorMessage));
        }
        VerificationReportFullDto reportFullDto = VerificationReportDtoMapper.mapToFullDto(reportOpt.get());
        return ResponseEntity.ok(reportFullDto);
    }

    public ResponseEntity<?> deleteById(long id){
        Optional<VerificationReport> reportOpt = reportRepository.findById(id);
        if (reportOpt.isEmpty()){
            String errorMessage = "Отчет № " + id + " не найден";
            log.info(errorMessage);
            return ResponseEntity.status(404).body(new ServiceMessage(errorMessage));
        }
        reportRepository.deleteById(id);
        String okMessage = "Отчет № " + id + " успешно удален";
        log.info(okMessage);
        return ResponseEntity.ok().body(okMessage);
    }

}
