package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import main.arshin.entities.vri.VriItem;
import main.dto.rest.VerificationReportDto;
import main.dto.rest.VerificationReportFullDto;
import main.dto.rest.mappers.VerificationRecordDtoMapper;
import main.dto.rest.mappers.VerificationReportDtoMapper;
import main.exception.ArshinResponseException;
import main.exception.DtoCompositionException;
import main.model.VerificationRecord;
import main.model.VerificationReport;
import main.repository.VerificationReportRepository;
import main.service.ServiceMessage;
import main.service.interfaces.ArshinDataService;
import main.service.interfaces.VerificationRecordService;
import main.service.interfaces.VerificationReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Getter
@Setter
@Service
@Slf4j
public class VerificationReportServiceImpl implements VerificationReportService {
    @Autowired
    private final VerificationReportRepository reportRepository;
    @Autowired
    private final VerificationRecordService verificationRecordService;
    @Autowired
    private final ArshinDataService arshinDataService;

    public VerificationReportServiceImpl(VerificationReportRepository reportRepository, VerificationRecordService verificationRecordService, ArshinDataService arshinDataService) {
        this.reportRepository = reportRepository;
        this.verificationRecordService = verificationRecordService;
        this.arshinDataService = arshinDataService;
    }


    @Override
    public ResponseEntity<?> save(VerificationReportFullDto reportDto){
        try {
            checkVerificationReportDtoComposition(reportDto);
            VerificationReport report = VerificationReportDtoMapper.mapToEntity(reportDto);
            reportRepository.save(report);
            String okMessage = "Отчет о поверке сохранен";
            log.info(okMessage);
            return ResponseEntity.status(201).body(new ServiceMessage(okMessage));
        } catch (DtoCompositionException ex){
            return ResponseEntity.status(400).body(
                    new ServiceMessage(ex.getMessage()));
        }
    }

    @Override
    public ResponseEntity<?> findById(long id){
        try {
            VerificationReport reportFromDb = getReportById(id);
            VerificationReport report = updateSentToArshinFromReportIfConditionsMet(reportFromDb);
            VerificationReportFullDto reportFullDto = VerificationReportDtoMapper.mapToFullDto(report);
            return ResponseEntity.ok(reportFullDto);
        } catch (EntityNotFoundException ex){
            log.info(ex.getMessage());
            return ResponseEntity.status(404).body(new ServiceMessage(ex.getMessage()));
        }
    }


    private  VerificationReport updateSentToArshinFromReportIfConditionsMet(VerificationReport report){
        if (!report.isSentToArshin() && report.isReadyToSend()) {
            boolean allArshinNumbersIsPresent = true;
            for (VerificationRecord record : report.getRecords()) {
                boolean arshinNumberIsPresent = (record.getArshinVerificationNumber() != null && !record.getArshinVerificationNumber().isEmpty());
                allArshinNumbersIsPresent = allArshinNumbersIsPresent && arshinNumberIsPresent;
            }
            if (allArshinNumbersIsPresent) {
                report.setReadyToSend(false);
                report.setSentToArshin(true);
                reportRepository.save(report);
            }
        }
        return report;
    }

    @Override
    public VerificationReport getReportById(long id){
        Optional<VerificationReport> reportOpt = reportRepository.findById(id);
        if (reportOpt.isEmpty()){
            throw new EntityNotFoundException("Отчет № " + id + " не найден");
        }
        return reportOpt.get();
    }

    @Override
    public List<VerificationReport> getReadyToSendReports(){
        return reportRepository.findByReadyToSend(true);
    }

    @Override
    public List<VerificationReport> getPublicToArshinReportsAndNotSendToFsa(){
        return reportRepository.findByPublicToArshinAndSentToFsa(true, false);
    }

    @Override
    public Page<VerificationReportDto> findAll(Pageable pageable){
        return reportRepository.findAll(pageable).map(VerificationReportDtoMapper::mapToDto);
    }


    @Override
    public ResponseEntity<?> update(VerificationReportFullDto reportDto){
        try {
            checkVerificationReportDtoComposition(reportDto);
            VerificationReport reportFromDB = getReportById(reportDto.getId());
            reportDto.getRecords().forEach(recordDto -> {
                VerificationRecord record = VerificationRecordDtoMapper.mapToEntity(recordDto);
                if (!reportFromDB.getRecords().contains(record)) {
                    reportFromDB.addRecord(record);
                }
            });
            reportFromDB.update(VerificationReportDtoMapper.mapToEntity(reportDto));
            reportRepository.save(reportFromDB);
            String okMessage = "Отчет о поверке обновлен";
            log.info(okMessage);
            return ResponseEntity.ok().body(new ServiceMessage(okMessage));
        } catch (EntityNotFoundException ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(404).body(
                    new ServiceMessage(ex.getMessage()));
        } catch (DtoCompositionException ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(400).body(
                    new ServiceMessage(ex.getMessage()));
        }
    }
    @Override
    public ResponseEntity<?> updateReportFromArshin(long id) {
        try {

            String okMessage = "Номера записей о поверке в ФГИС Аршин успешно получены";
            VerificationReport report = getReportById(id);
            if (report.isPublicToArshin()){
                log.info("Номера записей о поверке в отчете № {} получены ранее, получения данных из ФГИС \"Аршин\" не требуется", report.getId());
                return ResponseEntity.ok().body(new ServiceMessage(okMessage));
            }
            for (VerificationRecord record : report.getRecords()) {
                VriItem item = arshinDataService.findVerificationRecordsData(record);
                verificationRecordService.updateArshinVerificationNumber(record.getId(), item.getResultDocnum());
                Thread.sleep(100);
            }
            setPublicToArshinStatus(report);
            log.info(okMessage);
            return ResponseEntity.ok().body(new ServiceMessage(okMessage));
        } catch (ArshinResponseException ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(500).body(new ServiceMessage(ex.getMessage()));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public ResponseEntity<?> updateSentToArshinReportsFromArshin(){
        List<VerificationReport> sentToArshinReports = reportRepository.findBySentToArshin(true);
        sentToArshinReports.forEach(report -> updateReportFromArshin(report.getId()));
        String okMessage = "Номера записей о поверке в ФГИС Аршин успешно получены";
        return ResponseEntity.ok().body(new ServiceMessage(okMessage));
    }

    private void checkVerificationReportDtoComposition(VerificationReportFullDto reportDto) throws DtoCompositionException {
        if (reportDto.getRecords().isEmpty()){
            throw new DtoCompositionException("Отчет должен содержать хотя бы одну запись о поверке средства измерений");
        }
    }

    @Override
    public ResponseEntity<?> delete(long id){
        reportRepository.deleteById(id);
        String okMessage = "Отчет № " + id + " успешно удален";
        log.info(okMessage);
        return ResponseEntity.ok().body(okMessage);
    }

    @Override
    public void setSentToArshinStatus(VerificationReport report){
        report.setReadyToSend(false);
        report.setSentToArshin(true);
        reportRepository.save(report);
    }

    @Override
    public void setPublicToArshinStatus(VerificationReport report){
        report.setReadyToSend(false);
        report.setSentToArshin(true);
        report.setPublicToArshin(true);
        reportRepository.save(report);
    }

    @Override
    public void setSentToFsaStatus(VerificationReport report){
        report.setSentToFsa(true);
        reportRepository.save(report);
    }



}
