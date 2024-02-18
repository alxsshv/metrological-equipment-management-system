package main.service.verificationissue;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import main.dto.RecordDto;
import main.dto.VerificationIssueDto;
import main.dto.mappers.IssueDtoMapper;
import main.model.VerificationIssue;
import main.model.VerificationRecord;
import main.repository.VerificationIssueRepository;
import main.repository.VerificationRecordRepository;
import main.service.ServiceMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@NoArgsConstructor
@Service
public class VerificationIssueService implements IVerificationIssueService {
    @Autowired
    private VerificationIssueRepository verificationIssueRepository;
    @Autowired
    private VerificationRecordRepository verificationRecordRepository;

    @Override
    public ResponseEntity<?> addIssue(VerificationIssueDto issueDto) {
        verificationIssueRepository.save(IssueDtoMapper.mapIssueDtoToEntity(issueDto));
        String okMessage = "Заявка успешно зарегистрирована";
        System.out.println(okMessage);
        return ResponseEntity.ok(
                new ServiceMessage(okMessage));
    }

    @Override
    public ResponseEntity<?> updateRecord(RecordDto recordDto){
        Optional<VerificationRecord> recordOpt = verificationRecordRepository.findById(recordDto.getId());
        if (recordOpt.isEmpty()){
            String errorMessage = "Запись № " + recordDto.getNumberVerification() + " о поверке СИ " +
                    recordDto.getTypeMeasuringInstrument() + " не найдена";
            return ResponseEntity.status(404).body(new ServiceMessage(errorMessage));
        }
        VerificationRecord updatingData = IssueDtoMapper.mapRecordDtoToEntity(recordDto);
        VerificationRecord recordFromDb = recordOpt.get();
        recordFromDb.updateFrom(updatingData);
        verificationRecordRepository.save(recordFromDb);
        String okMessage ="Cведения о записи № " + recordFromDb.getNumberVerification() + " о поверке СИ  " +
                updatingData.getTypeMeasuringInstrument() + " успешно обновлены";
        System.out.println(okMessage);
        return ResponseEntity.ok(new ServiceMessage(okMessage));
    }

    @Override
    public ResponseEntity<?> findById(int id) {
        Optional<VerificationIssue> issueOpt = verificationIssueRepository.findById(id);
        if (issueOpt.isPresent()) {
            VerificationIssueDto issueDto = IssueDtoMapper.mapIssueToDto(issueOpt.get());
            return ResponseEntity.ok(issueDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @Override
    public ResponseEntity<?> findRecordById(int id) {
        Optional<VerificationRecord> recordOpt = verificationRecordRepository.findById(id);
        if (recordOpt.isPresent()) {
            RecordDto recordDto = IssueDtoMapper.mapRecordToDto(recordOpt.get());
            return ResponseEntity.ok(recordDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public List<VerificationIssueDto> findAll() {
        return verificationIssueRepository.findAll()
                .stream().map(IssueDtoMapper::mapIssueToDto).toList();
    }


}
