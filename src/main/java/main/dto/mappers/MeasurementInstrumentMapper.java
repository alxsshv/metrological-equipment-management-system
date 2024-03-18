package main.dto.mappers;

import main.dto.MeasurementInstrumentDto;
import main.model.MeasurementInstrument;

public class MeasurementInstrumentMapper {
    public static MeasurementInstrumentDto mapToDto(MeasurementInstrument measurementInstrument){
        MeasurementInstrumentDto dto = new MeasurementInstrumentDto();
        dto.setId(measurementInstrument.getId());
        dto.setMiTypeId(measurementInstrument.getMiTypeId());
        dto.setModification(measurementInstrument.getModification());
        dto.setSerialNum(measurementInstrument.getSerialNum());
        dto.setInventoryNum(measurementInstrument.getInventoryNum());
        dto.setOwner(dto.getOwner());
        dto.setManufactureYear(measurementInstrument.getManufactureYear());
        dto.setCommissioningYear(measurementInstrument.getCommissioningYear());
        return dto;
    }
    public static MeasurementInstrument mapToEntity (MeasurementInstrumentDto dto){
        MeasurementInstrument measurementInstrument = new MeasurementInstrument();
        measurementInstrument.setId(dto.getId());
        measurementInstrument.setMiTypeId(dto.getMiTypeId());
        measurementInstrument.setModification(dto.getModification());
        measurementInstrument.setSerialNum(dto.getSerialNum());
        measurementInstrument.setInventoryNum(dto.getInventoryNum());
        measurementInstrument.setOwner(dto.getOwner());
        measurementInstrument.setCommissioningYear(dto.getCommissioningYear());
        measurementInstrument.setManufactureYear(dto.getManufactureYear());
        return measurementInstrument;
    }
}
