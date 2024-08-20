package main.dto.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class MiTypeDetailsDto {
    private long id;
    @Valid
    private MiTypeDto miType;
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


}
