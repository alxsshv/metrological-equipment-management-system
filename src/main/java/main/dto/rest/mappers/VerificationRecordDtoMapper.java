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
        record.setInapplicableReason(dto.getInapplicableReason());
        record.setPressure(dto.getPressure());
        record.setArshinVerificationNumber(dto.getArshinVerificationNumber());
        record.setMi(dto.getMi());
        record.setEmployee(dto.getEmployee());
        dto.getMiStandards().forEach(standard -> record.getMiStandards().add(MiStandardDtoMapper.mapToEntity(standard)));
        dto.getVerificationMis().forEach(mi -> record.getVerificationMis().add(mi));
        record.setShortVerification(dto.isShortVerification());
        record.setShortVerificationCharacteristic(dto.getShortVerificationCharacteristic());
        return record;
    }



    public static VerificationRecordDto mapToDto(VerificationRecord record){
        VerificationRecordDto dto = new VerificationRecordDto();
        dto.setId(record.getId());
        dto.setVerificationType(record.getVerificationType());
        dto.setVerificationDate(DateStringConverter.getStringOrNull(record.getVerificationDate()));
        dto.setValidDate(DateStringConverter.getStringOrNull(record.getValidDate()));
        dto.setApplicable(record.isApplicable());
        dto.setInapplicableReason(record.getInapplicableReason());
        dto.setTemperature(record.getTemperature());
        dto.setHumidity(record.getHumidity());
        dto.setPressure(record.getPressure());
        dto.setArshinVerificationNumber(record.getArshinVerificationNumber());
        dto.setMi(record.getMi());
        dto.setEmployee(record.getEmployee());
        dto.setMiStandards(record.getMiStandards().stream().map(MiStandardDtoMapper::mapToDto).toList());
        dto.setVerificationMis(record.getVerificationMis().stream().toList());
        dto.setShortVerification(record.isShortVerification());
        dto.setShortVerificationCharacteristic(record.getShortVerificationCharacteristic());
        return dto;
    }
}
