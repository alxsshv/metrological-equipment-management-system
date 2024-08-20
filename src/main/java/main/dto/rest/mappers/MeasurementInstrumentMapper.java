package main.dto.rest.mappers;

import main.dto.rest.MiDto;
import main.dto.rest.MiFullDto;
import main.dto.rest.mappers.utils.DateStringConverter;
import main.model.MeasurementInstrument;
import org.modelmapper.ModelMapper;

import java.time.format.DateTimeFormatter;

public class MeasurementInstrumentMapper {

    public static MiFullDto mapToFullDto(MeasurementInstrument measurementInstrument){
        ModelMapper modelMapper = new ModelMapper();
        MiFullDto miFullDto = modelMapper.map(measurementInstrument, MiFullDto.class);
        miFullDto.setMiType(MiTypeDtoMapper.mapToDto(measurementInstrument.getMiType()));
        miFullDto.setOwner(OrganizationDtoMapper.mapToDto(measurementInstrument.getOwner()));
        miFullDto.setVerificationDate(DateStringConverter.getStringOrNull(measurementInstrument.getVerificationDate()));
        miFullDto.setValidDate(DateStringConverter.getStringOrNull(measurementInstrument.getValidDate()));
        miFullDto.setCreationDateTime(measurementInstrument.getCreationDateTime().format(DateTimeFormatter.ISO_DATE_TIME));
        if (measurementInstrument.getUpdatingDateTime() != null){
            miFullDto.setUpdatingDateTime(measurementInstrument.getUpdatingDateTime().format(DateTimeFormatter.ISO_DATE_TIME));
        }
        return miFullDto;
    }

    public static MiDto mapToDto(MeasurementInstrument measurementInstrument){
        ModelMapper modelMapper = new ModelMapper();
        MiDto miDto = modelMapper.map(measurementInstrument, MiDto.class);
        miDto.setOwner(measurementInstrument.getOwner().getNotation());
        miDto.setVerificationDate(DateStringConverter.getStringOrNull(measurementInstrument.getVerificationDate()));
        miDto.setValidDate(DateStringConverter.getStringOrNull(measurementInstrument.getValidDate()));
        return miDto;
    }

    public static MeasurementInstrument mapToEntity(MiFullDto miFullDto){
        ModelMapper modelMapper = new ModelMapper();
        MeasurementInstrument mi = modelMapper.map(miFullDto, MeasurementInstrument.class);
        mi.setMiType(MiTypeDtoMapper.mapToEntity(miFullDto.getMiType()));
        mi.setVerificationDate(DateStringConverter.parseLocalDateOrGetNull(miFullDto.getVerificationDate()));
        mi.setValidDate(DateStringConverter.parseLocalDateOrGetNull(miFullDto.getValidDate()));
        mi.setOwner(OrganizationDtoMapper.mapToEntity(miFullDto.getOwner()));
        return mi;
    }
}
