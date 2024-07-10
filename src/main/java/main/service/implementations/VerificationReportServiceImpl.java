package main.service.implementations;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import main.arshin.ArshinHttpClient;
import main.arshin.VerificationRequestBuilder;
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
import main.service.interfaces.VerificationReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    private final String arshinVerificationUri;
    @Autowired
    private final VerificationReportRepository reportRepository;
    @Autowired
    private final VerificationRecordServiceImpl verificationRecordService;
    @Autowired
    private final SettingsServiceImpl settingsService;

    public VerificationReportServiceImpl(@Value("${arshin.verification.uri}") String arshinVerificationUri, VerificationReportRepository reportRepository, VerificationRecordServiceImpl verificationRecordService, SettingsServiceImpl settingsService) {
        this.arshinVerificationUri = arshinVerificationUri;
        this.reportRepository = reportRepository;
        this.verificationRecordService = verificationRecordService;
        this.settingsService = settingsService;
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
            VerificationReport report = getReportById(id);
            String verificationOrganization = settingsService.getSettings().getOrganizationNotation();
            for (VerificationRecord record : report.getRecords()) {
                String verificationRequest = new VerificationRequestBuilder()
                        .uri("https://fgis.gost.ru/fundmetrology/eapi/vri?")
                        .miModification(record.getMi().getModification())
                        .miNumber(record.getMi().getSerialNum())
                        .orgTitle(verificationOrganization)
                        .verificationDate(record.getVerificationDate())
                        .build();
                VriItem item = ArshinHttpClient.getVerificationItemIfOnlyMatches(verificationRequest);
                verificationRecordService.updateArshinVerificationNumber(record.getId(), item.getResultDocnum());
                Thread.sleep(800);
            }
            String okMessage = "Номера записей о поверке в ФГИС Аршин успешно получены";
            report.setReadyToSend(false);
            report.setSentToArshin(true);
            report.setPublicToArshin(true);
            reportRepository.save(report);
            log.info(okMessage);
            return ResponseEntity.ok().body(new ServiceMessage(okMessage));
        } catch (ArshinResponseException ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(500).body(new ServiceMessage(ex.getMessage()));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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



}
