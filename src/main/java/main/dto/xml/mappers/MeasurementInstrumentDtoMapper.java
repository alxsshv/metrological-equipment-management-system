package main.dto.xml.mappers;

import main.dto.xml.fsa.MeasurementInstrumentDto;
import main.model.VerificationRecord;

public class MeasurementInstrumentDtoMapper {
    public static MeasurementInstrumentDto mapRecordToDto(VerificationRecord record){
        MeasurementInstrumentDto instrument = new MeasurementInstrumentDto();
        instrument.setNumberVerification(record.getNumberVerification());
        instrument.setTypeMeasuringInstrument(record.getTypeMeasuringInstrument());
        instrument.setDateVerification(record.getDateVerification().substring(0,10));
        instrument.setDateEndVerification(record.getDateEndVerification().substring(0,10));
        instrument.setApprovedEmployee(
                ApprovedEmployeeDtoMappers.mapEmployeeToDto(record.getEmployee()));
        instrument.setResultVerification(record.getResultVerification());
        return instrument;
    }
}
