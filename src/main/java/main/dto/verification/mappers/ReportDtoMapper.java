package main.dto.verification.mappers;

import main.dto.mappers.EmployeeDtoMapper;
import main.dto.verification.RecordDto;
import main.dto.verification.ReportDto;
import main.model.verification.Record;
import main.model.verification.Report;

import java.time.format.DateTimeFormatter;

public class ReportDtoMapper {
    public static ReportDto mapReportToDto(Report report){
        ReportDto dto = new ReportDto();
        dto.setId(report.getId());
        dto.setRecords(report.getRecords()
                .stream().map(ReportDtoMapper::mapRecordToDto).toList());
        dto.setCreationDateTime(report.getCreationDateTime().format(DateTimeFormatter.ISO_DATE_TIME));
        return dto;
    }

    public static Report mapReportDtoToEntity(ReportDto dto){
        Report report = new Report();
        report.setId(dto.getId());
        report.setRecords(dto.getRecords()
                .stream().map(ReportDtoMapper::mapRecordDtoToEntity).toList());
        return report;
    }

    public static RecordDto mapRecordToDto(Record record){
        RecordDto dto = new RecordDto();
        dto.setId(record.getId());
        dto.setArshinRecordNumber(record.getArshinRecordNumber());
        dto.setMiTypeNumber(record.getMiTypeNumber());
        dto.setModification(record.getModification());
        dto.setSerialNumber(record.getSerialNumber());
        dto.setMiOwner(record.getMiOwner());
        dto.setVerificationType(record.getVerificationType());
        dto.setVerificationDate(record.getVerificationDate());
        dto.setValidDate(record.getValidDate());
        dto.setProcedureDocNumber(record.getProcedureDocNumber());
        dto.setEmployee(EmployeeDtoMapper.mapToDto(record.getEmployee()));
        dto.setCalibration(record.isCalibration());
        dto.setResult(record.isResult());
        dto.setTemperature(record.getTemperature());
        dto.setPressure(record.getPressure());
        dto.setHumidity(record.getHumidity());
        dto.setNote(record.getNote());
        return dto;
    }

    public static Record mapRecordDtoToEntity(RecordDto dto) {
        Record record = new Record();
        record.setId(dto.getId());
        if (dto.getArshinRecordNumber() == null){
            dto.setArshinRecordNumber("");
        }
        record.setArshinRecordNumber(dto.getArshinRecordNumber());
        record.setMiTypeNumber(dto.getMiTypeNumber());
        record.setModification(dto.getModification());
        record.setSerialNumber(dto.getSerialNumber());
        record.setMiOwner(dto.getMiOwner());
        record.setVerificationType(dto.getVerificationType());
        record.setVerificationDate(dto.getVerificationDate());
        record.setValidDate(dto.getValidDate());
        record.setProcedureDocNumber(dto.getProcedureDocNumber());
        record.setEmployee(EmployeeDtoMapper.mapToEntity(dto.getEmployee()));
        record.setCalibration(dto.isCalibration());
        record.setResult(dto.isResult());
        record.setTemperature(dto.getTemperature());
        record.setPressure(dto.getPressure());
        record.setHumidity(dto.getHumidity());
        record.setNote(dto.getNote());
        return record;
    }
}
