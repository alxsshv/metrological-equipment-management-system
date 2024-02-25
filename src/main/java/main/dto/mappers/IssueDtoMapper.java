package main.dto.mappers;

import main.dto.VerificationRecordDto;
import main.dto.VerificationIssueDto;
import main.model.VerificationIssue;
import main.model.VerificationRecord;

import java.time.format.DateTimeFormatter;

public class IssueDtoMapper {
    public static VerificationIssueDto mapIssueToDto(VerificationIssue issue){
        VerificationIssueDto dto = new VerificationIssueDto();
        dto.setId(issue.getId());
        dto.setVerificationRecords(issue.getVerificationRecords()
                .stream().map(IssueDtoMapper::mapRecordToDto).toList());
        dto.setCreationDateTime(issue.getCreationDateTime().format(DateTimeFormatter.ISO_DATE_TIME));
        return dto;
    }

    public static VerificationIssue mapIssueDtoToEntity(VerificationIssueDto dto){
        VerificationIssue issue = new VerificationIssue();
        issue.setId(dto.getId());
        issue.setVerificationRecords(dto.getVerificationRecords()
                .stream().map(IssueDtoMapper::mapRecordDtoToEntity).toList());
        return issue;
    }

    public static VerificationRecordDto mapRecordToDto(VerificationRecord record){
        VerificationRecordDto dto = new VerificationRecordDto();
        dto.setId(record.getId());
        dto.setNumberVerification(record.getNumberVerification());
        dto.setTypeMeasuringInstrument(record.getTypeMeasuringInstrument());
        dto.setDateVerification(record.getDateVerification());
        dto.setDateEndVerification(record.getDateEndVerification());
        dto.setEmployee(EmployeeDtoMapper.mapToDto(record.getEmployee()));
        dto.setResultVerification(record.getResultVerification());
        return dto;
    }

    public static VerificationRecord mapRecordDtoToEntity(VerificationRecordDto dto) {
        VerificationRecord record = new VerificationRecord();
        record.setId(dto.getId());
        record.setNumberVerification(dto.getNumberVerification());
        record.setTypeMeasuringInstrument(dto.getTypeMeasuringInstrument());
        record.setDateVerification(dto.getDateVerification());
        record.setDateEndVerification(dto.getDateEndVerification());
        record.setEmployee(EmployeeDtoMapper.mapToEntity(dto.getEmployee()));
        record.setResultVerification(dto.getResultVerification());
        return record;
    }
}
