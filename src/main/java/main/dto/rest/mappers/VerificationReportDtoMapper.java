package main.dto.rest.mappers;

import main.dto.rest.VerificationRecordDto;
import main.dto.rest.VerificationReportDto;
import main.dto.rest.VerificationReportFullDto;
import main.dto.rest.mappers.utils.DateStringConverter;
import main.model.VerificationRecord;
import main.model.VerificationReport;

public class VerificationReportDtoMapper {
    public static VerificationReport mapToEntity(VerificationReportFullDto dto){
        VerificationReport report = new VerificationReport();
        report.setId(dto.getId());
        report.setComment(dto.getComment());
        addRecordsToReportFromReportDto(report, dto);
        return report;
    }

    private static void addRecordsToReportFromReportDto(VerificationReport report, VerificationReportFullDto reportDto){
        for (VerificationRecordDto recordDto : reportDto.getRecords()){
            VerificationRecord record = VerificationRecordDtoMapper.mapToEntity(recordDto);
            report.addRecord(record);
        }
    }

    public static VerificationReportFullDto mapToFullDto(VerificationReport report){
        VerificationReportFullDto dto = new VerificationReportFullDto();
        dto.setId(report.getId());
        dto.setComment(report.getComment());
        dto.setRecords((report.getRecords().stream().map(VerificationRecordDtoMapper::mapToDto)).toList());
        dto.setCreationDate(DateStringConverter.getISOStringOrNull(report.getCreationDate()));
        dto.setUpdateDate(DateStringConverter.getISOStringOrNull(report.getUpdateDate()));
        return dto;
    }

    public static VerificationReportDto mapToDto(VerificationReport report){
        VerificationReportDto dto = new VerificationReportDto();
        dto.setId(report.getId());
        dto.setComment(report.getComment());
        dto.setCreationDate(DateStringConverter.getISOStringOrNull(report.getCreationDate()));
        dto.setUpdateDate(DateStringConverter.getISOStringOrNull(report.getUpdateDate()));
        return dto;
    }
}
