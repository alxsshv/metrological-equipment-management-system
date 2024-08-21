package main.dto.rest.mappers;

import main.dto.rest.*;
import main.model.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class MiDetailsDtoMapper {

    public static MiDetails mapToEntity(MiDetailsDto miDetailsDto){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        MiDetails miDetails = new MiDetails();
        miDetails.setId(miDetailsDto.getId());
        miDetails.setInventoryNum(miDetailsDto.getInventoryNum());
        miDetails.setMiRegistryNumber(miDetailsDto.getMiRegistryNumber());
        miDetails.setManufactureDate(miDetailsDto.getManufactureDate());
        miDetails.setStartUseDate(miDetailsDto.getStartUseDate());
        miDetails.setMi(MeasurementInstrumentDtoMapper.mapToEntity(miDetailsDto.getMiFullDto()));
        miDetails.setCondition(modelMapper.map(miDetailsDto.getCondition(), MiCondition.class));
        miDetails.setDepartment(modelMapper.map(miDetailsDto.getDepartment(), Department.class));
        miDetails.setStatus(modelMapper.map(miDetailsDto.getStatus(), MiStatus.class));
        miDetails.setUser(miDetailsDto.getUser());
        miDetails.setMeasCategory(modelMapper.map(miDetailsDto.getMeasCategory(), MeasCategory.class));
        return miDetails;
    }

    public static MiDetailsDto mapToDto(MiDetails miDetails){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        MiDetailsDto miDetailsDto = new MiDetailsDto();
        miDetailsDto.setUser(miDetails.getUser());
        miDetailsDto.setId(miDetails.getId());
        miDetailsDto.setInventoryNum(miDetails.getInventoryNum());
        miDetailsDto.setMiRegistryNumber(miDetails.getMiRegistryNumber());
        miDetailsDto.setManufactureDate(miDetails.getManufactureDate());
        miDetailsDto.setStartUseDate(miDetails.getStartUseDate());
        miDetailsDto.setMeasCategory(modelMapper.map(miDetails.getMeasCategory(), MeasCategoryDto.class));
        miDetailsDto.setDepartment(modelMapper.map(miDetails.getDepartment(), DepartmentDto.class));
        miDetailsDto.setCondition(modelMapper.map(miDetails.getCondition(), MiConditionDto.class));
        miDetailsDto.setStatus(modelMapper.map(miDetails.getStatus(), MiStatusDto.class));
        MiFullDto miFullDto = MeasurementInstrumentDtoMapper.mapToFullDto(miDetails.getMi());
        miDetailsDto.setMiFullDto(miFullDto);
        return miDetailsDto;
    }
}
