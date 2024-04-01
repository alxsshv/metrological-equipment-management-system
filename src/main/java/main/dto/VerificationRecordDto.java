package main.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class VerificationRecordDto {
    private long id;
    private String numberVerification;
    private String dateVerification;
    private String dateEndVerification;
    private String typeMeasuringInstrument;
    private EmployeeDto employee;
    private int resultVerification;

    @Override
    public String toString() {
        return "RecordDto{" +
                "id=" + id +
                ", numberVerification='" + numberVerification + '\'' +
                ", dateVerification='" + dateVerification + '\'' +
                ", dateEndVerification='" + dateEndVerification + '\'' +
                ", typeMeasuringInstrument='" + typeMeasuringInstrument + '\'' +
                '}';
    }
}
