package main.dto.rest;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class MiTypeDto {
    private long id;
    @Pattern(
            regexp = "[0-9]{3,5}-[0-9]{2}",
            message = "Некорректно указан регистрационный номер типа средства измерений" +
                    " в Федеральном информационном фонде по обеспечению единства измерений"
    )
    private String number;
    @NotEmpty(message = "Пожалуйста заполните наименование типа средства измерений")
    private String title;
    @NotEmpty(message = "Пожалуйста заполните обозначение типа средства измерений")
    private String notation;
    private String startDate;
    private String endDate;
    private String miTitleTemplate;
    private double verificationPeriod;
}
