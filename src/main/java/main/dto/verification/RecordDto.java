package main.dto.verification;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.dto.EmployeeDto;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RecordDto {
    private long id;
    private String arshinRecordNumber;
    private String miTypeNumber;
    private String modification;
    private String serialNumber;
    private String miOwner;
    private String verificationDate;
    private String validDate;
    private int verificationType;
    private boolean calibration;
    private String procedureDocNumber;
    private boolean result;
    private EmployeeDto employee;
    private double temperature; //Celseus
    private double pressure; // kPa
    private double humidity; // percent
    private String note;
    private List<String> standards;

}
