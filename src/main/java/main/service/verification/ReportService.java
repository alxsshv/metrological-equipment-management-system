package main.service.verification;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import main.dto.verification.RecordDto;
import main.dto.verification.ReportDto;
import main.dto.verification.mappers.ReportDtoMapper;
import main.model.verification.Record;
import main.model.verification.Report;
import main.repository.verification.ReportRepository;
import main.repository.verification.RecordRepository;
import main.service.ServiceMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@NoArgsConstructor
@Service
public class ReportService implements IReportService {
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private RecordRepository recordRepository;

    @Override
    public ResponseEntity<?> addIssue(ReportDto reportDto) {
        reportRepository.save(ReportDtoMapper.mapReportDtoToEntity(reportDto));
        String okMessage = "Отчет добавлен";
        System.out.println(okMessage);
        return ResponseEntity.ok(
                new ServiceMessage(okMessage));
    }

    @Override
    public ResponseEntity<?> updateRecord(RecordDto recordDto){
        Optional<Record> recordOpt = recordRepository.findById(recordDto.getId());
        if (recordOpt.isEmpty()){
            String errorMessage = "Запись о поверке СИ " +
                    recordDto.getModification() + " зав. №" +
                    recordDto.getSerialNumber() + " не найдена";
            return ResponseEntity.status(404).body(new ServiceMessage(errorMessage));
        }
        Record updatingData = ReportDtoMapper.mapRecordDtoToEntity(recordDto);
        Record recordFromDb = recordOpt.get();
        recordFromDb.updateFrom(updatingData);
        recordRepository.save(recordFromDb);
        String okMessage ="Cведения о поверке " + recordFromDb.getModification() + " зав. № "
                + recordFromDb.getSerialNumber() + " успешно обновлены";
        System.out.println(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }

    @Override
    public ResponseEntity<?> findById(int id) {
        Optional<Report> reportOpt = reportRepository.findById(id);
        if (reportOpt.isPresent()) {
            ReportDto reportDto = ReportDtoMapper.mapReportToDto(reportOpt.get());
            return ResponseEntity.ok(reportDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @Override
    public ResponseEntity<?> findRecordById(int id) {
        Optional<Record> recordOpt = recordRepository.findById(id);
        if (recordOpt.isPresent()) {
            RecordDto recordDto = ReportDtoMapper.mapRecordToDto(recordOpt.get());
            return ResponseEntity.ok(recordDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public List<ReportDto> findAll() {
        return reportRepository.findAll()
                .stream().map(ReportDtoMapper::mapReportToDto).toList();
    }

    public ResponseEntity<?> deleteReport(int id){
        Optional<Report> reportOpt = reportRepository.findById(id);
        if (reportOpt.isPresent()) {
          for (Record record : reportOpt.get().getRecords()){
              recordRepository.delete(record);
          }
          reportRepository.delete(reportOpt.get());
          String okMessage ="Отчет успешно удален";
          System.out.println(okMessage);
          return ResponseEntity.ok(new ServiceMessage(okMessage));
        }
        String errorMessage ="Запись о поверке № " + id + " не найдена";
        System.out.println(errorMessage);
        return ResponseEntity.status(404).body(errorMessage);
    }


}
