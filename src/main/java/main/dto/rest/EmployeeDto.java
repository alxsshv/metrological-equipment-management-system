package main.dto.rest;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Setter
@Getter
public class EmployeeDto {
    private long id;
    @NotEmpty(message = "Пожалуйста заполните фамилию поверителя")
    private String surname;
    @NotEmpty(message = "Пожалуйста заполните имя поверителя")
    private String name;
    @NotEmpty(message = "Пожалуйста заполните отчетство поверителя")
    private String patronymic;
    @Pattern(regexp = "[0-9]{11}", message = "Неверный формат СНИЛС")
    private String snils;
}
