package main.dto.rest;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Setter
@Getter
public class EmployeeDto {
    private long id;
    @NotNull(message = "Укажите пользователя, имеющего статус поверителя")
    private UserDto userDto;
    @Pattern(regexp = "[0-9]{11}", message = "Неверный формат СНИЛС")
    private String snils;
}
