package main.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MiType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String number; //Номер в реестре утвержденного типа СИ
    private String title; //Наименование типа СИ
    private String notation; //Обозначение типа СИ
    private LocalDate startDate; //Начало срока действия
    private LocalDate endDate; //Окончание срока действия
    private String verificationPeriod; //межповерочный интервал
    private List<String> modifications; // Модификации
    private String instructionNotation; //Обозначение методики поверки
    private String instructionTitle; // Наименование методики поверки
    private double humidityLowLimit;
    private double humidityHiLimit;
    private double temperatureLowLimit;
    private double temperatureHiLimit;
    private double pressureLowLimit;
    private double pressureHiLimit;

    public void updateFrom(MiType newData){
        number = newData.getNumber();
        title = newData.getTitle();
        notation = newData.getNotation();
        startDate = newData.getStartDate();
        endDate = newData.getEndDate();
        verificationPeriod = newData.getVerificationPeriod();
        modifications = newData.getModifications();
        instructionNotation = newData.getInstructionNotation();
        instructionTitle = newData.getInstructionTitle();
        humidityLowLimit = newData.getHumidityLowLimit();
        humidityHiLimit = newData.getHumidityHiLimit();
        temperatureHiLimit = newData.getTemperatureHiLimit();
        temperatureLowLimit = newData.getTemperatureLowLimit();
        pressureHiLimit = newData.getPressureHiLimit();
        pressureLowLimit = newData.getPressureLowLimit();
    }
}
