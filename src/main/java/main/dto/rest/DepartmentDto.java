package main.dto.rest;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DepartmentDto {
    private long id;
    @NotNull(message = "Пожалуйста заполните обозначение подразделения")
    private String notation;
}
