package main.service.implementations;

import main.dto.VerificationReportDto;
import main.dto.VerificationReportFullDto;
import main.dto.mappers.VerificationReportDtoMapper;
import main.model.VerificationReport;
import main.repository.VerificationReportRepository;
import main.service.ServiceMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
        return ResponseEntity.ok().body(new ServiceMessage(okMessage));
    }

    private String checkVerificationReportDtoComposition(VerificationReportFullDto reportDto){
        if (reportDto.getRecordDtos().isEmpty()){
            return "Отчет должен содержать хотябы одну запись о поверке средства измерений";
        }
        return "";
    }

    public Page<VerificationReportDto> getAllWithPagination(Pageable pageable){
        return reportRepository.findAll(pageable).map(VerificationReportDtoMapper::mapToDto);
    }

}
