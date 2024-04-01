package main.dto.mappers;

import main.dto.MiStandardDto;
import main.model.MiStandard;

public class MiStandardDtoMapper {
    public static MiStandardDto mapToDto(MiStandard standard){
        MiStandardDto dto = new MiStandardDto();
        dto.setArshinNumber(standard.getArshinNumber());
        dto.setMeasurementInstrument(standard.getMeasurementInstrument());
        dto.setSchemaType(standard.getSchemaType());
        dto.setSchemaTitle(standard.getSchemaTitle());
        dto.setLevelCode(standard.getLevelCode());
        dto.setLevelTitle(standard.getLevelTitle());
        dto.setStateStandardNumber(standard.getStateStandardNumber());
        dto.setCreationDateTime(standard.getCreationDateTime().toString());
        dto.setUpdatingDateTime(standard.getUpdatingDateTime().toString());
        return dto;
    }

    public static MiStandard mapToEntity(MiStandardDto dto){
        MiStandard standard = new MiStandard();
        standard.setArshinNumber(dto.getArshinNumber());
        standard.setMeasurementInstrument(dto.getMeasurementInstrument());
        standard.setSchemaTitle(dto.getSchemaTitle());
        standard.setSchemaType(dto.getSchemaType());
        standard.setLevelCode(dto.getLevelCode());
        standard.setLevelTitle(dto.getLevelTitle());
        standard.setStateStandardNumber(dto.getStateStandardNumber());
        return standard;
    }
}
