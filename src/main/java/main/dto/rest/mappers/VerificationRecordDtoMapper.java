package main.dto.rest.mappers;


import main.dto.rest.VerificationRecordDto;
import main.dto.rest.mappers.utils.DateStringConverter;
import main.model.VerificationRecord;


public class VerificationRecordDtoMapper {
    public static VerificationRecord mapToEntity(VerificationRecordDto dto){
        VerificationRecord record = new VerificationRecord();
        record.setId(dto.getId());
        record.setMi(dto.getMi());
        record.setVerificationType(dto.getVerificationType());
        record.setVerificationDate(DateStringConverter.parseLocalDateOrGetNull(dto.getVerificationDate()));
        record.setValidDate(DateStringConverter.parseLocalDateOrGetNull(dto.getValidDate()));
        record.setApplicable(dto.isApplicable());
        record.setTemperature(dto.getTemperature());
        record.setHumidity(dto.getHumidity());
        record.setPressure(dto.getPressure());
        record.setArshinVerificationNumber(dto.getArshinVerificationNumber());
        record.setMi(dto.getMi());
        record.setEmployee(dto.getEmployee());
        dto.getMiStandards().forEach(standard -> record.getMiStandards().add(standard));
        return record;
    }



    public static VerificationRecordDto mapToDto(VerificationRecord record){
        VerificationRecordDto dto = new VerificationRecordDto();
        dto.setId(record.getId());
        dto.setVerificationType(record.getVerificationType());
        dto.setVerificationDate(DateStringConverter.getStringOrNull(record.getVerificationDate()));
        dto.setValidDate(DateStringConverter.getStringOrNull(record.getValidDate()));
        dto.setApplicable(record.isApplicable());
        dto.setTemperature(record.getTemperature());
        dto.setHumidity(record.getHumidity());
        dto.setPressure(record.getPressure());
        dto.setArshinVerificationNumber(record.getArshinVerificationNumber());
        dto.setMi(record.getMi());
        dto.setEmployee(record.getEmployee());
        dto.setMiStandards(record.getMiStandards().stream().toList());
        return dto;
    }
}
