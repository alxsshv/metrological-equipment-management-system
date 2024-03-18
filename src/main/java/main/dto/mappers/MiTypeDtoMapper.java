package main.dto.mappers;

import main.dto.MiTypeDto;
import main.dto.MiTypeFullDto;
import main.model.MiType;
import main.model.MiTypeInstruction;


public class MiTypeDtoMapper {
    public static MiTypeDto mapToDto(MiType type){
        MiTypeDto dto = new MiTypeDto();
        dto.setId(type.getId());
        dto.setNumber(type.getNumber());
        dto.setNotation(type.getNotation());
        dto.setTitle(type.getTitle());
        dto.setStartDate(type.getStartDate());
        dto.setEndDate(type.getEndDate());
        dto.setVerificationPeriod(type.getVerificationPeriod());
        return dto;
    }

    public static MiTypeFullDto mapToFullDto(MiType type){
        MiTypeFullDto dto = new MiTypeFullDto();
        dto.setId(type.getId());
        dto.setNumber(type.getNumber());
        dto.setNotation(type.getNotation());
        dto.setTitle(type.getTitle());
        dto.setModifications(type.getModifications());
        dto.setStartDate(type.getStartDate());
        dto.setEndDate(type.getEndDate());
        dto.setVerificationPeriod(type.getVerificationPeriod());
        dto.setInstructionTitle(type.getInstruction().getInstructionTitle());
        dto.setInstructionNotation(type.getInstruction().getInstructionNotation());
        dto.setPressureHiLimit(type.getInstruction().getPressureHiLimit());
        dto.setPressureLowLimit(type.getInstruction().getPressureLowLimit());
        dto.setHumidityHiLimit(type.getInstruction().getHumidityHiLimit());
        dto.setHumidityLowLimit(type.getInstruction().getHumidityLowLimit());
        dto.setTemperatureHiLimit(type.getInstruction().getTemperatureHiLimit());
        dto.setTemperatureLowLimit(type.getInstruction().getTemperatureLowLimit());
        dto.setModifications(type.getModifications());
        return dto;
    }

    public static MiType mapToEntity (MiTypeFullDto dto){
        MiType type = new MiType();
        type.setId(dto.getId());
        type.setNumber(dto.getNumber());
        assert dto.getNotation() != null;
        if (dto.getNotation().isEmpty() | dto.getNotation().equals("-")) {
            type.setNotation("Обозначение отсутствует");
        } else {
            type.setNotation(dto.getNotation());
        }
        type.setTitle(dto.getTitle());
        type.setModifications(dto.getModifications());
        type.setStartDate(dto.getStartDate());
        type.setEndDate(dto.getEndDate());
        type.setVerificationPeriod(dto.getVerificationPeriod());
        type.setModifications(dto.getModifications());
        type.setInstruction(getMiTypeInstruction(dto));
        return type;
    }

    private static MiTypeInstruction getMiTypeInstruction(MiTypeFullDto dto) {
        MiTypeInstruction instruction = new MiTypeInstruction();
        instruction.setInstructionTitle(dto.getInstructionTitle());
        instruction.setInstructionNotation(dto.getInstructionNotation());
        instruction.setPressureHiLimit(dto.getPressureHiLimit());
        instruction.setPressureLowLimit(dto.getPressureLowLimit());
        instruction.setHumidityHiLimit(dto.getHumidityHiLimit());
        instruction.setHumidityLowLimit(dto.getHumidityLowLimit());
        instruction.setTemperatureHiLimit(dto.getTemperatureHiLimit());
        instruction.setTemperatureLowLimit(dto.getTemperatureLowLimit());
        return instruction;
    }
}
