package main.dto.rest.mappers;

import main.dto.rest.*;
import main.model.*;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class MiDetailsMapper {

    public static MiDetails mapToEntity(MiDetailsDto miDetailsDto){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        MiDetails miDetails = modelMapper.map(miDetailsDto, MiDetails.class);
        miDetails.setMi(MeasurementInstrumentMapper.mapToEntity(miDetailsDto.getMiFullDto()));
        miDetails.setCondition(modelMapper.map(miDetailsDto.getCondition(), MiCondition.class));
        miDetails.setDepartment(modelMapper.map(miDetails.getDepartment(), Department.class));
        miDetails.setStatus(modelMapper.map(miDetails.getStatus(), MiStatus.class));
        miDetails.setMeasCategory(modelMapper.map(miDetails.getMeasCategory(), MeasCategory.class));
        return miDetails;
    }

    public static MiDetailsDto mapToDto(MiDetails miDetails){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        MiDetailsDto miDetailsDto = modelMapper.map(miDetails, MiDetailsDto.class);
        miDetailsDto.setMeasCategory(modelMapper.map(miDetails.getMeasCategory(), MeasCategoryDto.class));
        miDetailsDto.setDepartment(modelMapper.map(miDetails.getDepartment(), DepartmentDto.class));
        miDetailsDto.setCondition(modelMapper.map(miDetails.getCondition(), MiConditionDto.class));
        miDetailsDto.setStatus(modelMapper.map(miDetails.getStatus(), MiStatusDto.class));
        MiFullDto miFullDto = MeasurementInstrumentMapper.mapToFullDto(miDetails.getMi());
        miDetailsDto.setMiFullDto(miFullDto);
        return miDetailsDto;
    }
}
