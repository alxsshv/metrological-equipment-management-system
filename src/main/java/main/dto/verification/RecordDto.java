package main.dto.verification;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.dto.EmployeeDto;

@Getter
@Setter
@NoArgsConstructor
public class RecordDto {
    private int id;
    private String arshinRecordNumber;
    private String miTypeNumber;
    private String modification;
    private String serialNumber;
    private String miOwner;
    private String verificationDate;
    private String validDate;
    private boolean result;
    private int verificationType;
    private boolean calibration;
    private String stickerNumber;
    private String procedureDocNumber;
    private EmployeeDto employee;
    private String standard;
    private double temperature; //Celseus
    private double pressure; // kPa
    private double humidity; // percent
    private String note;

}
