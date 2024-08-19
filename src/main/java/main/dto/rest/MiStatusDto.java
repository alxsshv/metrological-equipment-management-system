package main.dto.rest;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MiStatusDto {
    private long id;
    @NotEmpty(message = "Пожалуйста заполните наименование статуса СИ")
    private String status;
}
