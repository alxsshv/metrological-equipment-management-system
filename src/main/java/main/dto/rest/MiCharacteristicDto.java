package main.dto.rest;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MiCharacteristicDto {
    private long id;
    @NotEmpty(message = "Пожалуйста заполните наименование характеристики")
    private String title;
    @NotEmpty(message = "Пожалуйста заполните значение характеристики")
    private String value;
    @NotEmpty(message = "Пожалуйста заполните единицу измерения")
    private String unit;
    @NotNull(message = "Для вносимой характеристики не указано средство измерений")
    private MiDetailsDto miDetailsDto;
}
