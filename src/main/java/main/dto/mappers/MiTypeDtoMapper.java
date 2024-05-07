package main.dto.mappers;

import main.dto.MiTypeDto;
import main.dto.MiTypeFullDto;
import main.model.MiType;
import main.model.MiTypeInstruction;
import main.model.MiTypeModification;

import java.util.ArrayList;
import java.util.List;


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

    public static MiTypeFullDto mapToFullDto(MiTypeInstruction instruction){
        MiTypeFullDto dto = new MiTypeFullDto();
        dto.setId(instruction.getMiType().getId());
        dto.setNumber(instruction.getMiType().getNumber());
        dto.setNotation(instruction.getMiType().getNotation());
        dto.setTitle(instruction.getMiType().getTitle());
        dto.setMiTitleTemplate(instruction.getMiType().getMiTitleTemplate());
        dto.setModifications(instruction.getMiType().getModifications().stream().map(MiTypeModification::getNotation).toList());
        dto.setStartDate(instruction.getMiType().getStartDate());
        dto.setEndDate(instruction.getMiType().getEndDate());
        dto.setVerificationPeriod(instruction.getMiType().getVerificationPeriod());
        dto.setInstructionTitle(instruction.getInstructionTitle());
        dto.setInstructionNotation(instruction.getInstructionNotation());
        dto.setPressureHiLimit(instruction.getPressureHiLimit());
        dto.setPressureLowLimit(instruction.getPressureLowLimit());
        dto.setHumidityHiLimit(instruction.getHumidityHiLimit());
        dto.setHumidityLowLimit(instruction.getHumidityLowLimit());
        dto.setTemperatureHiLimit(instruction.getTemperatureHiLimit());
        dto.setTemperatureLowLimit(instruction.getTemperatureLowLimit());
        return dto;
    }

    public static MiTypeInstruction mapToEntity (MiTypeFullDto fullDto){
        MiTypeInstruction instruction = new MiTypeInstruction();
        instruction.setInstructionTitle(fullDto.getInstructionTitle());
        instruction.setInstructionNotation(fullDto.getInstructionNotation());
        instruction.setPressureHiLimit(fullDto.getPressureHiLimit());
        instruction.setPressureLowLimit(fullDto.getPressureLowLimit());
        instruction.setHumidityHiLimit(fullDto.getHumidityHiLimit());
        instruction.setHumidityLowLimit(fullDto.getHumidityLowLimit());
        instruction.setTemperatureHiLimit(fullDto.getTemperatureHiLimit());
        instruction.setTemperatureLowLimit(fullDto.getTemperatureLowLimit());
        instruction.setMiType(getMiType(fullDto));
        return instruction;
    }

    private static MiType getMiType(MiTypeFullDto fullDto) {
        MiType type = new MiType();
        type.setId(fullDto.getId());
        type.setNumber(fullDto.getNumber());
        if (fullDto.getNotation().isEmpty() | fullDto.getNotation().equals("-")) {
            type.setNotation("Обозначение отсутствует");
        } else {
            type.setNotation(fullDto.getNotation());
        }
        type.setTitle(fullDto.getTitle());
        type.setMiTitleTemplate(fullDto.getMiTitleTemplate());
        type.setModifications(getModifications(fullDto));
        type.setStartDate(fullDto.getStartDate());
        type.setEndDate(fullDto.getEndDate());
        type.setVerificationPeriod(fullDto.getVerificationPeriod());
        return type;
    }

    private static List<MiTypeModification> getModifications(MiTypeFullDto fullDto){
        List<MiTypeModification> modifications = new ArrayList<>();
        for (String s : fullDto.getModifications()){
            MiTypeModification modification = new MiTypeModification();
            modification.setNotation(s);
            modifications.add(modification);
        }
        return modifications;
    }
}
