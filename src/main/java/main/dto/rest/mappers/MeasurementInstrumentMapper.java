package main.dto.rest.mappers;

import main.dto.rest.MiDto;
import main.dto.rest.MiFullDto;
import main.dto.rest.mappers.utils.DateStringConverter;
import main.model.MeasurementInstrument;


import java.time.format.DateTimeFormatter;

public class MeasurementInstrumentMapper {
    public static MiDto mapToDto(MeasurementInstrument measurementInstrument){
        MiDto dto = new MiDto();
        dto.setId(measurementInstrument.getId());
        dto.setTitle(measurementInstrument.getTitle());
        dto.setModification(measurementInstrument.getModification());
        dto.setSerialNum(measurementInstrument.getSerialNum());
        dto.setVerificationDate(DateStringConverter.getStringOrNull(measurementInstrument.getVerificationDate()));
        dto.setValidDate(DateStringConverter.getStringOrNull(measurementInstrument.getValidDate()));
        dto.setApplicable(measurementInstrument.isApplicable());
        dto.setOwner(measurementInstrument.getOwner().getNotation());
        return dto;
    }
    public static MiFullDto mapToFullDto(MeasurementInstrument measurementInstrument){
        MiFullDto dto = new MiFullDto();
        dto.setId(measurementInstrument.getId());
        dto.setMiType(measurementInstrument.getMiType());
        dto.setTitle(measurementInstrument.getTitle());
        dto.setModification(measurementInstrument.getModification());
        dto.setSerialNum(measurementInstrument.getSerialNum());
        dto.setInventoryNum(measurementInstrument.getInventoryNum());
        dto.setVerificationDate(measurementInstrument.getVerificationDate().toString());
        dto.setValidDate(DateStringConverter.getStringOrNull(measurementInstrument.getValidDate()));
        dto.setApplicable(measurementInstrument.isApplicable());
        dto.setManufactureDate(DateStringConverter.getStringOrNull(measurementInstrument.getManufactureDate()));
        dto.setStartUseDate(DateStringConverter.getStringOrNull(measurementInstrument.getStartUseDate()));
        dto.setOwner(measurementInstrument.getOwner());
        dto.setUser(measurementInstrument.getUser());
        dto.setCreationDateTime(measurementInstrument.getCreationDateTime().format(DateTimeFormatter.ISO_DATE_TIME));
        if (measurementInstrument.getUpdatingDateTime() != null){
        dto.setUpdatingDateTime(measurementInstrument.getUpdatingDateTime().format(DateTimeFormatter.ISO_DATE_TIME));
        }
        return dto;
    }
    public static MeasurementInstrument mapToEntity (MiFullDto dto){
        MeasurementInstrument measurementInstrument = new MeasurementInstrument();
        measurementInstrument.setId(dto.getId());
        measurementInstrument.setMiType(dto.getMiType());
        measurementInstrument.setTitle(dto.getMiType().getMiTitleTemplate());
        measurementInstrument.setModification(dto.getModification());
        measurementInstrument.setSerialNum(dto.getSerialNum());
        measurementInstrument.setInventoryNum(dto.getInventoryNum());
        measurementInstrument.setVerificationDate(DateStringConverter.parseLocalDateOrGetNull(dto.getVerificationDate()));
        measurementInstrument.setValidDate(DateStringConverter.parseLocalDateOrGetNull(dto.getValidDate()));
        measurementInstrument.setApplicable(dto.isApplicable());
        measurementInstrument.setOwner(dto.getOwner());
        measurementInstrument.setStartUseDate(DateStringConverter.parseLocalDateOrGetNull(dto.getStartUseDate()));
        measurementInstrument.setManufactureDate(DateStringConverter.parseLocalDateOrGetNull(dto.getManufactureDate()));
        measurementInstrument.setUser(dto.getUser());
        return measurementInstrument;
    }
}
