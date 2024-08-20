package main.dto.rest.mappers;

import main.dto.rest.MiTypeDto;
import main.dto.rest.mappers.utils.DateStringConverter;
import main.model.MiType;


public class MiTypeDtoMapper {
    public static MiTypeDto mapToDto(MiType type){
        MiTypeDto dto = new MiTypeDto();
        dto.setId(type.getId());
        dto.setNumber(type.getNumber());
        dto.setNotation(type.getNotation());
        dto.setTitle(type.getTitle());
        dto.setMiTitleTemplate(type.getMiTitleTemplate());
        dto.setStartDate(DateStringConverter.getStringOrNull(type.getStartDate()));
        dto.setEndDate(DateStringConverter.getStringOrNull(type.getEndDate()));
        dto.setVerificationPeriod(type.getVerificationPeriod());
        return dto;
    }


    public static MiType mapToEntity(MiTypeDto dto) {
        MiType type = new MiType();
        type.setId(dto.getId());
        type.setNumber(dto.getNumber());
        if (dto.getNotation().isEmpty() | dto.getNotation().equals("-")) {
            type.setNotation("Обозначение отсутствует");
        } else {
            type.setNotation(dto.getNotation());
        }
        type.setMiTitleTemplate(dto.getMiTitleTemplate());
        type.setTitle(dto.getTitle());
        type.setStartDate(DateStringConverter.parseLocalDateOrGetNull(dto.getStartDate()));
        type.setEndDate(DateStringConverter.parseLocalDateOrGetNull(dto.getEndDate()));
        type.setVerificationPeriod(dto.getVerificationPeriod());
        return type;
    }

}
