package main.dto.rest.mappers;

import main.dto.rest.MiStandardDto;
import main.model.MiStandard;

import static main.dto.rest.mappers.utils.DateStringConverter.getISOStringOrNull;

public class MiStandardDtoMapper {
    public static MiStandardDto mapToDto(MiStandard standard){
        MiStandardDto dto = new MiStandardDto();
        dto.setId(standard.getId());
        dto.setArshinNumber(standard.getArshinNumber());
        dto.setMiDetails(MiDetailsDtoMapper.mapToDto(standard.getMiDetails()));
        dto.setSchemaType(standard.getSchemaType());
        dto.setSchemaTitle(standard.getSchemaTitle());
        dto.setSchemaNotation(standard.getSchemaNotation());
        dto.setLevel(standard.getLevel());
        dto.setStateStandardNumber(standard.getStateStandardNumber());
        dto.setCreationDateTime(getISOStringOrNull(standard.getCreationDateTime()));
        dto.setUpdatingDateTime(getISOStringOrNull(standard.getUpdatingDateTime()));
        return dto;
    }



    public static MiStandard mapToEntity(MiStandardDto dto){
        MiStandard standard = new MiStandard();
        standard.setId(dto.getId());
        standard.setArshinNumber(dto.getArshinNumber());
        standard.setMiDetails(MiDetailsDtoMapper.mapToEntity(dto.getMiDetails()));
        standard.setSchemaTitle(dto.getSchemaTitle());
        standard.setSchemaType(dto.getSchemaType());
        standard.setSchemaNotation(dto.getSchemaNotation());
        standard.setLevel(dto.getLevel());
        standard.setStateStandardNumber(dto.getStateStandardNumber());
        return standard;
    }
}
