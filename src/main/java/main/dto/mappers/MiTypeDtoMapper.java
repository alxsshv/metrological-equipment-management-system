package main.dto.mappers;

import main.dto.EmployeeDto;
import main.dto.MiTypeDto;
import main.model.Employee;
import main.model.MiType;


public class MiTypeDtoMapper {
    public static MiTypeDto mapToDto(MiType type){
        MiTypeDto dto = new MiTypeDto();
        dto.setId(type.getId());
        dto.setNumber(type.getNumber());
        dto.setNotation(type.getNotation());
        dto.setTitle(type.getTitle());
        dto.setModifications(type.getModifications());
        dto.setStartDate(type.getStartDate());
        dto.setEndDate(type.getEndDate());
        dto.setVerificationPeriod(type.getVerificationPeriod());
        dto.setInstructionTitle(type.getInstructionTitle());
        dto.setInstructionNotation(type.getInstructionNotation());
        dto.setPressureHiLimit(type.getPressureHiLimit());
        dto.setPressureLowLimit(type.getPressureLowLimit());
        dto.setHumidityHiLimit(type.getHumidityHiLimit());
        dto.setHumidityLowLimit(type.getHumidityLowLimit());
        dto.setTemperatureHiLimit(type.getTemperatureHiLimit());
        dto.setTemperatureLowLimit(type.getTemperatureLowLimit());
        dto.setModifications(type.getModifications());
        return dto;
    }
    public static MiType mapToEntity (MiTypeDto dto){
        MiType type = new MiType();
        type.setId(dto.getId());
        type.setNumber(dto.getNumber());
        type.setNotation(dto.getNotation());
        type.setTitle(dto.getTitle());
        type.setModifications(dto.getModifications());
        type.setStartDate(dto.getStartDate());
        type.setEndDate(dto.getEndDate());
        type.setVerificationPeriod(dto.getVerificationPeriod());
        type.setInstructionTitle(dto.getInstructionTitle());
        type.setInstructionNotation(dto.getInstructionNotation());
        type.setPressureHiLimit(dto.getPressureHiLimit());
        type.setPressureLowLimit(dto.getPressureLowLimit());
        type.setHumidityHiLimit(dto.getHumidityHiLimit());
        type.setHumidityLowLimit(dto.getHumidityLowLimit());
        type.setTemperatureHiLimit(dto.getTemperatureHiLimit());
        type.setTemperatureLowLimit(dto.getTemperatureLowLimit());
        type.setModifications(dto.getModifications());
        return type;
    }
}
