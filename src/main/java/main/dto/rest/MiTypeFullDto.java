package main.dto.rest;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class MiTypeFullDto {
    private long id;
    @Pattern(
            regexp = "[0-9]{3,5}-[0-9]{2}",
            message = "Некорректно указан регистрационный номер типа средства измерений" +
                    " в Федеральном информационном фонде по обеспечению единства измерений"
    )
    private String number;
    @NotEmpty(message = "Пожалуйста заполните наименование типа средства измерений")
    private String title;
    private String notation;
    private String startDate;
    private String endDate;
    private double verificationPeriod;
    private String miTitleTemplate;
    @NotEmpty(message = "Пожалуйста укажите не менее одной модификации типа средства измерений," +
            " если модификации отсутствуют, введите единственную модифкацию," +
            " соответствующую обозначению типа СИ или фразу \"Модификация отсутствует\"")
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
