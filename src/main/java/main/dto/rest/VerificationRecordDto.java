package main.dto.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.model.Employee;
import main.model.MeasurementInstrument;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerificationRecordDto {
    private long id;
    private int verificationType; // 1 - первичная, 2 - периодическая
    private String verificationDate;
    private String validDate;
    private boolean applicable;
    private String inapplicableReason;
    private double temperature; //deg. Celseus
    private double pressure; //kPa
    private double humidity; // percents
    private String arshinVerificationNumber;
    private MeasurementInstrument mi;
    private Employee employee;
    private List<MiStandardDto> miStandards = new ArrayList<>();
    private List<MeasurementInstrument> verificationMis = new ArrayList<>();
    private boolean  calibration;
    private String stickerNum;
    private boolean signPass;
    private boolean signMi;
    private boolean shortVerification;
    private String shortVerificationCharacteristic;

    @Override
    public String toString() {
        return "VerificationRecordDto{" +
                "id=" + id +
                ", miStandards=" + miStandards +
                '}';
    }
}
