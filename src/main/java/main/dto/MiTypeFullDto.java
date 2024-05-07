package main.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class MiTypeFullDto {
    private long id;
    private String number;
    private String title;
    private String notation;
    private LocalDate startDate;
    private LocalDate endDate;
    private double verificationPeriod;
    private String miTitleTemplate;
    private List<String> modifications = new ArrayList<>();
    private String instructionNotation;
    private String instructionTitle;
    private double humidityLowLimit;
    private double humidityHiLimit;
    private double temperatureLowLimit;
    private double temperatureHiLimit;
    private double pressureLowLimit;
    private double pressureHiLimit;

    @Override
    public String toString() {
        return "MiTypeFullDto{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", title='" + title + '\'' +
                ", notation='" + notation + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", verificationPeriod=" + verificationPeriod +
                ", miTitleTemplate='" + miTitleTemplate + '\'' +
                ", modifications=" + modifications +
                ", instructionNotation='" + instructionNotation + '\'' +
                ", instructionTitle='" + instructionTitle + '\'' +
                ", humidityLowLimit=" + humidityLowLimit +
                ", humidityHiLimit=" + humidityHiLimit +
                ", temperatureLowLimit=" + temperatureLowLimit +
                ", temperatureHiLimit=" + temperatureHiLimit +
                ", pressureLowLimit=" + pressureLowLimit +
                ", pressureHiLimit=" + pressureHiLimit +
                '}';
    }
}
