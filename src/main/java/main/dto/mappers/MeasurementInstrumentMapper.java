package main.dto.mappers;

import main.dto.MiDto;
import main.dto.MiFullDto;
import main.model.MeasurementInstrument;

public class MeasurementInstrumentMapper {
    public static MiDto mapToDto(MeasurementInstrument measurementInstrument){
        MiDto dto = new MiDto();
        dto.setId(measurementInstrument.getId());
        dto.setMiTypeTitle(measurementInstrument.getMiType().getTitle());
        dto.setModification(measurementInstrument.getModification());
        dto.setSerialNum(measurementInstrument.getSerialNum());
        dto.setVerificationDate(measurementInstrument.getVerificationDate());
        dto.setValidDate(measurementInstrument.getValidDate());
        dto.setApplicable(measurementInstrument.isApplicable());
        dto.setOwner(measurementInstrument.getOwner().getNotation());
        return dto;
    }
    public static MiFullDto mapToFullDto(MeasurementInstrument measurementInstrument){
        MiFullDto dto = new MiFullDto();
        dto.setId(measurementInstrument.getId());
        dto.setMiType(measurementInstrument.getMiType());
        dto.setModification(measurementInstrument.getModification());
        dto.setSerialNum(measurementInstrument.getSerialNum());
        dto.setInventoryNum(measurementInstrument.getInventoryNum());
        dto.setVerificationDate(measurementInstrument.getVerificationDate());
        dto.setValidDate(measurementInstrument.getValidDate());
        dto.setApplicable(measurementInstrument.isApplicable());
        dto.setManufactureDate(measurementInstrument.getManufactureDate());
        dto.setStartUseDate(measurementInstrument.getStartUseDate());
        dto.setOwner(measurementInstrument.getOwner());
        dto.setUser(measurementInstrument.getUser());
        dto.setCreationDateTime(measurementInstrument.getCreationDateTime().toString());
        if (measurementInstrument.getUpdatingDateTime()!= null) {
            dto.setUpdatingDateTime(measurementInstrument.getUpdatingDateTime().toString());
        }
        return dto;
    }
    public static MeasurementInstrument mapToEntity (MiFullDto dto){
        MeasurementInstrument measurementInstrument = new MeasurementInstrument();
        measurementInstrument.setId(dto.getId());
        measurementInstrument.setMiType(dto.getMiType());
        measurementInstrument.setModification(dto.getModification());
        measurementInstrument.setSerialNum(dto.getSerialNum());
        measurementInstrument.setInventoryNum(dto.getInventoryNum());
        measurementInstrument.setVerificationDate(dto.getVerificationDate());
        measurementInstrument.setValidDate(dto.getValidDate());
        measurementInstrument.setApplicable(dto.isApplicable());
        measurementInstrument.setOwner(dto.getOwner());
        measurementInstrument.setStartUseDate(dto.getStartUseDate());
        measurementInstrument.setManufactureDate(dto.getManufactureDate());
        measurementInstrument.setUser(dto.getUser());
        return measurementInstrument;
    }
}
