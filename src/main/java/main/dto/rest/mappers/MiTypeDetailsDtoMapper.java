package main.dto.rest.mappers;

import main.dto.rest.MiTypeDetailsDto;
import main.model.MiTypeDetails;
import main.model.MiTypeModification;

import java.util.ArrayList;
import java.util.List;


public class MiTypeDetailsDtoMapper {

    public static MiTypeDetailsDto mapToDto(MiTypeDetails miTypeDetails){
        MiTypeDetailsDto dto = new MiTypeDetailsDto();
        dto.setId(miTypeDetails.getMiType().getId());
        dto.setMiType(MiTypeDtoMapper.mapToDto(miTypeDetails.getMiType()));
        dto.setModifications(getModificationNotationsList(miTypeDetails));
        dto.setInstructionTitle(miTypeDetails.getInstructionTitle());
        dto.setInstructionNotation(miTypeDetails.getInstructionNotation());
        dto.setPressureHiLimit(miTypeDetails.getPressureHiLimit());
        dto.setPressureLowLimit(miTypeDetails.getPressureLowLimit());
        dto.setHumidityHiLimit(miTypeDetails.getHumidityHiLimit());
        dto.setHumidityLowLimit(miTypeDetails.getHumidityLowLimit());
        dto.setTemperatureHiLimit(miTypeDetails.getTemperatureHiLimit());
        dto.setTemperatureLowLimit(miTypeDetails.getTemperatureLowLimit());
        return dto;
    }

    private static List<String> getModificationNotationsList(MiTypeDetails miTypeDetails){
        return miTypeDetails.getModifications().stream().map(MiTypeModification::getNotation).toList();
    }

    public static MiTypeDetails mapToEntity(MiTypeDetailsDto miTypeDetailsDto){
        MiTypeDetails miTypeDetails = new MiTypeDetails();
        miTypeDetails.setInstructionTitle(miTypeDetailsDto.getInstructionTitle());
        miTypeDetails.setInstructionNotation(miTypeDetailsDto.getInstructionNotation());
        miTypeDetails.setPressureHiLimit(miTypeDetailsDto.getPressureHiLimit());
        miTypeDetails.setPressureLowLimit(miTypeDetailsDto.getPressureLowLimit());
        miTypeDetails.setHumidityHiLimit(miTypeDetailsDto.getHumidityHiLimit());
        miTypeDetails.setHumidityLowLimit(miTypeDetailsDto.getHumidityLowLimit());
        miTypeDetails.setTemperatureHiLimit(miTypeDetailsDto.getTemperatureHiLimit());
        miTypeDetails.setTemperatureLowLimit(miTypeDetailsDto.getTemperatureLowLimit());
        miTypeDetails.setModifications(getModifications(miTypeDetailsDto));
        miTypeDetails.setMiType(MiTypeDtoMapper.mapToEntity(miTypeDetailsDto.getMiType()));
        return miTypeDetails;
    }

    private static List<MiTypeModification> getModifications(MiTypeDetailsDto miTypeDetailsDto){
        List<MiTypeModification> modifications = new ArrayList<>();
        for (String s : miTypeDetailsDto.getModifications()){
            MiTypeModification modification = new MiTypeModification();
            modification.setNotation(s);
            modifications.add(modification);
        }
        return modifications;
    }

}
