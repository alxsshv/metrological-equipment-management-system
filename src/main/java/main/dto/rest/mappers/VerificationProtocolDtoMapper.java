package main.dto.rest.mappers;


import main.dto.rest.VerificationProtocolDto;
import main.dto.rest.mappers.utils.DateStringConverter;
import main.model.VerificationProtocol;

public class VerificationProtocolDtoMapper {
    public static VerificationProtocol map(VerificationProtocolDto dto){
        VerificationProtocol protocol = new VerificationProtocol();
        protocol.setId(dto.getId());
        protocol.setNumber(dto.getNumber());
        protocol.setStorageFileName(dto.getStorageFileName());
        protocol.setOriginalFilename(dto.getOriginalFilename());
        protocol.setSignedFileName(dto.getSignedFileName());
        protocol.setDescription(dto.getDescription());
        protocol.setInstrument(MeasurementInstrumentDtoMapper.mapToEntity(dto.getInstrumentDto()));
        protocol.setAwaitingSigning(dto.isAwaitingSigning());
        protocol.setSigned(dto.isSigned());
        protocol.setVerificationDate(DateStringConverter.parseLocalDateOrGetNull(dto.getVerificationDate()));
        protocol.setVerificationEmployee(UserDtoMapper.mapToEntity(dto.getVerificationEmployee()));
        return protocol;
    }

    public static VerificationProtocolDto map(VerificationProtocol protocol) {
        VerificationProtocolDto dto = new VerificationProtocolDto();
        dto.setId(protocol.getId());
        dto.setNumber(protocol.getNumber());
        dto.setOriginalFilename(protocol.getOriginalFilename());
        dto.setDescription(protocol.getDescription());
        dto.setExtension(protocol.getExtension());
        dto.setJournalId(protocol.getJournal().getId());
        dto.setInstrumentDto(MeasurementInstrumentDtoMapper.mapToFullDto(protocol.getInstrument()));
        dto.setSigned(protocol.isSigned());
        dto.setUploadingDate(DateStringConverter.getISOStringOrNull(protocol.getUploadingDate()));
        dto.setUpdatingDate(DateStringConverter.getISOStringOrNull(protocol.getUpdatingDate()));
        dto.setVerificationDate(DateStringConverter.getStringOrNull(protocol.getVerificationDate()));
        dto.setVerificationEmployee(UserDtoMapper.mapToDto(protocol.getVerificationEmployee()));
        return dto;
    }
}
