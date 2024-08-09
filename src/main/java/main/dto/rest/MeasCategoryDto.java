package main.dto.rest;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class MeasCategoryDto {
    private long id;
    @NotEmpty (message = "Пожалуйста заполните наименование вида измерений")
    private String title;
    private int number;
}
