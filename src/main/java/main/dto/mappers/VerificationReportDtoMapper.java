package main.dto.mappers;

import main.dto.VerificationRecordDto;
import main.dto.VerificationReportDto;
import main.model.VerificationRecord;
import main.model.VerificationReport;

public class VerificationReportDtoMapper {
    public static VerificationReport mapToEntityWithoutRecords(VerificationReportDto dto){
        VerificationReport report = new VerificationReport();
        report.setId(dto.getId());
        addRecordsToReportFromReportDto(report, dto);
        return report;
    }

    private static void addRecordsToReportFromReportDto(VerificationReport report, VerificationReportDto reportDto){
        for (VerificationRecordDto recordDto : reportDto.getRecordDtos()){
            VerificationRecord record = VerificationRecordDtoMapper.mapToEntity(recordDto);
            report.addRecord(record);
        }
    }

    public static VerificationReportDto mapToDto(VerificationReport report){
        VerificationReportDto dto = new VerificationReportDto();
        dto.setId(report.getId());
        dto.setRecordDtos((report.getRecords().stream().map(VerificationRecordDtoMapper::mapToDto)).toList());
        dto.setCreationDate(report.getCreationDate());
        dto.setUpdateDate(report.getUpdateDate());
        return dto;
    }
}
