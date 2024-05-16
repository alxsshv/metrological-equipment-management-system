package main.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.model.Employee;
import main.model.MeasurementInstrument;
import main.model.MiStandard;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class VerificationRecordDto {
    private long id;
    private int verificationType; // 1 - первичная, 2 - периодическая
    private LocalDate verificationDate;
    private LocalDate validDate;
    private boolean applicable;
    private double temperature; //deg. Celseus
    private double pressure; //kPa
    private double humidity; // percents
    private String arshinVerificationNumber;
    private MeasurementInstrument mi;
    private Employee employee;
    private List<MiStandard> miStandards;


}
