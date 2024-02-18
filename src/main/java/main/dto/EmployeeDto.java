package main.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Setter
@Getter
public class EmployeeDto {
    private int id;
    private String surname;
    private String name;
    private String patronymic;
    private String snils;
}
